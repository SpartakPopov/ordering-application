package com.example.ordering_app.business.impl;

import com.example.ordering_app.business.OrderService;
import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;
import com.example.ordering_app.persistence.MenuItemRepository;
import com.example.ordering_app.persistence.OrderRepository;
import com.example.ordering_app.persistence.impl.OrderItemRepositoryJPA;
import com.example.ordering_app.persistence.entity.OrderItemEntity;
import com.example.ordering_app.websocket.KitchenWebSocketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_DONE = "DONE";
    private static final String STATUS_COMPLETED = "COMPLETED";

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepositoryJPA orderItemRepositoryJPA;
    private final KitchenWebSocketService kitchenWebSocketService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            MenuItemRepository menuItemRepository,
                            OrderItemRepositoryJPA orderItemRepositoryJPA,
                            KitchenWebSocketService kitchenWebSocketService) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepositoryJPA = orderItemRepositoryJPA;
        this.kitchenWebSocketService = kitchenWebSocketService;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("An order must contain at least one item");
        } //reject empty orders

        for (OrderItem item : order.getItems()) {
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            } // reject zero or negative quantities

            menuItemRepository.findById(item.getMenuItemId())
                    .ifPresentOrElse(menuItem -> {
                        item.setMenuItemName(menuItem.getName()); // look up real name form DB
                        item.setMenuItemPrice(menuItem.getPrice()); // look up real price from DB
                        item.setSubtotal(menuItem.getPrice() * item.getQuantity()); // calculate subtotal
                        item.setStatus(STATUS_PENDING); // set initial status
                    }, () -> {
                        throw new IllegalArgumentException(
                                "Menu item not found with id: " + item.getMenuItemId()
                        );
                    });
        }


        double totalPrice = order.getItems().stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum(); // sum all subtotals to get total price

        order.setTotalPrice(totalPrice);
        order.setStatus(STATUS_PENDING);

        Order saved = orderRepository.save(order); // persist everything to DB


        kitchenWebSocketService.broadcastNewOrder(saved); // broadcast after saving 

        return saved;
    }

    @Override
    public Optional<Order> startOrder(int orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) return Optional.empty();

        Order order = orderOpt.get();
        if (!STATUS_PENDING.equals(order.getStatus())) return Optional.empty();

        order.setStatus(STATUS_IN_PROGRESS);
        Order saved = orderRepository.save(order);
        kitchenWebSocketService.broadcastOrderInProgress(orderId);
        return Optional.of(saved);
    }

    @Override
    public Optional<OrderItem> markItemInProgress(int orderId, int itemId) {
        Optional<OrderItemEntity> entityOpt = orderItemRepositoryJPA.findById(itemId);
        if (entityOpt.isEmpty()) return Optional.empty();

        OrderItemEntity itemEntity = entityOpt.get();
        if (!itemEntity.getOrder().getId().equals(orderId)) return Optional.empty();

        itemEntity.setStatus(STATUS_IN_PROGRESS);
        orderItemRepositoryJPA.save(itemEntity);

        // If the order is still PENDING, promote it to IN_PROGRESS
        orderRepository.findById(orderId).ifPresent(order -> {
            if (STATUS_PENDING.equals(order.getStatus())) {
                order.setStatus(STATUS_IN_PROGRESS);
                orderRepository.save(order);
            }
        });

        OrderItem updatedItem = new OrderItem(
                itemEntity.getId(),
                itemEntity.getMenuItemId(),
                itemEntity.getMenuItemName(),
                itemEntity.getMenuItemPrice(),
                itemEntity.getQuantity(),
                itemEntity.getSubtotal(),
                "IN_PROGRESS"
        );

        kitchenWebSocketService.broadcastItemInProgress(orderId, updatedItem);
        return Optional.of(updatedItem);
    }

    @Override
    public Optional<OrderItem> markItemDone(int orderId, int itemId) {
        Optional<OrderItemEntity> entityOpt = orderItemRepositoryJPA.findById(itemId);

        if (entityOpt.isEmpty()) {
            return Optional.empty();
        }

        OrderItemEntity itemEntity = entityOpt.get();


        if (!itemEntity.getOrder().getId().equals(orderId)) {
            return Optional.empty();
        } // item must belong to the order

        itemEntity.setStatus(STATUS_DONE);
        orderItemRepositoryJPA.save(itemEntity); // save DONE status to DB

        // Convert to domain for broadcasting
        OrderItem updatedItem = new OrderItem(
                itemEntity.getId(),
                itemEntity.getMenuItemId(),
                itemEntity.getMenuItemName(),
                itemEntity.getMenuItemPrice(),
                itemEntity.getQuantity(),
                itemEntity.getSubtotal(),
                STATUS_DONE
        );

        // Broadcast item done to all kitchen screens
        kitchenWebSocketService.broadcastItemDone(orderId, updatedItem);

        boolean allDone = itemEntity.getOrder().getItems().stream()
                .allMatch(i -> STATUS_DONE.equals(i.getStatus())); // check if every item in order is DONE

        if (allDone) {
            orderRepository.findById(orderId).ifPresent(order -> {
                order.setStatus(STATUS_COMPLETED);
                orderRepository.save(order); // if DONE mark it in DB
            });
            kitchenWebSocketService.broadcastOrderCompleted(orderId); // broadcast order COMPLETE

        }

        return Optional.of(updatedItem);
    }

    @Override
    public boolean cancelOrder(int id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}