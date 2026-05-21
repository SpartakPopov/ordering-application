package com.example.ordering_app.controller.mapper;

import com.example.ordering_app.controller.dto.CreateOrderRequest;
import com.example.ordering_app.controller.dto.OrderDTO;
import com.example.ordering_app.controller.dto.OrderItemDTO;
import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;

import java.util.List;
import java.util.stream.Collectors;


public class OrderMapper {

    public static Order toDomain(CreateOrderRequest request) {
        List<OrderItem> items = request.getItems().stream()
                .map(itemReq -> {
                    OrderItem item = new OrderItem();
                    item.setMenuItemId(itemReq.getMenuItemId());
                    item.setQuantity(itemReq.getQuantity());
                    return item;
                })
                .collect(Collectors.toList());

        Order order = new Order();
        order.setItems(items);
        return order;
    }

    public static OrderDTO toDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(OrderMapper::orderItemToDTO)
                .collect(Collectors.toList());

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