package com.example.ordering_app.controller.mapper;

import com.example.ordering_app.controller.dto.CreateOrderRequest;
import com.example.ordering_app.controller.dto.OrderDTO;
import com.example.ordering_app.controller.dto.OrderItemDTO;
import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;

import java.util.List;


public class OrderMapper {

    private OrderMapper() { /* utility class — do not instantiate */ }

    public static Order toDomain(CreateOrderRequest request) {
        List<OrderItem> items = request.getItems().stream()
                .map(itemReq -> {
                    OrderItem item = new OrderItem();
                    item.setMenuItemId(itemReq.getMenuItemId());
                    item.setQuantity(itemReq.getQuantity());
                    return item;
                })
                .toList();

        Order order = new Order();
        order.setItems(items);
        return order;
    }

    public static OrderDTO toDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(OrderMapper::orderItemToDTO)
                .toList();

        return new OrderDTO(
                order.getId(),
                itemDTOs,
                order.getStatus(),
                order.getTotalPrice()
        );
    }

    public static OrderItemDTO orderItemToDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getId(),
                item.getMenuItemId(),
                item.getMenuItemName(),
                item.getMenuItemPrice(),
                item.getQuantity(),
                item.getSubtotal(),
                item.getStatus()
        );
    }
}