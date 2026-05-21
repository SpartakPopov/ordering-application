package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;
import com.example.ordering_app.persistence.OrderRepository;
import com.example.ordering_app.persistence.entity.OrderEntity;
import com.example.ordering_app.persistence.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderRepositoryJPA orderRepositoryJPA;

    public OrderRepositoryImpl(OrderRepositoryJPA orderRepositoryJPA) {
        this.orderRepositoryJPA = orderRepositoryJPA;
    }

    @Override
    public List<Order> findAll() {
        return orderRepositoryJPA.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findById(int id) {
        return orderRepositoryJPA.findById(id).map(this::toDomain);
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = toEntity(order);
        OrderEntity saved = orderRepositoryJPA.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(int id) {
        return orderRepositoryJPA.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        orderRepositoryJPA.deleteById(id);
    }


    private Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems() != null
                ? entity.getItems().stream()
                .map(this::orderItemToDomain)
                .collect(Collectors.toList())
                : List.of();
        return new Order(entity.getId(), items, entity.getStatus(), entity.getTotalPrice());
    }

    private OrderItem orderItemToDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getId(),
                entity.getMenuItemId(),
                entity.getMenuItemName(),
                entity.getMenuItemPrice(),
                entity.getQuantity(),
                entity.getSubtotal(),
                entity.getStatus()
        );
    }

    private OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setStatus(order.getStatus());
        entity.setTotalPrice(order.getTotalPrice());

        if (order.getItems() != null) {
            List<OrderItemEntity> itemEntities = order.getItems().stream()
                    .map(item -> orderItemToEntity(item, entity))
                    .collect(Collectors.toList());
            entity.setItems(itemEntities);
        }
        return entity;
    }

    private OrderItemEntity orderItemToEntity(OrderItem item, OrderEntity orderEntity) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(item.getId());
        entity.setMenuItemId(item.getMenuItemId());
        entity.setMenuItemName(item.getMenuItemName());
        entity.setMenuItemPrice(item.getMenuItemPrice());
        entity.setQuantity(item.getQuantity());
        entity.setSubtotal(item.getSubtotal());
        entity.setOrder(orderEntity);
        return entity;
    }
}