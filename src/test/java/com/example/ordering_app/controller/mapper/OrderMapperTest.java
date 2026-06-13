package com.example.ordering_app.controller.mapper;

import com.example.ordering_app.controller.dto.CreateOrderRequest;
import com.example.ordering_app.controller.dto.OrderDTO;
import com.example.ordering_app.controller.dto.OrderItemDTO;
import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void toDomain_mapsItemsCorrectly() {
        CreateOrderRequest.OrderItemRequest itemReq = new CreateOrderRequest.OrderItemRequest();
        itemReq.setMenuItemId(1);
        itemReq.setQuantity(2);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setItems(List.of(itemReq));

        Order result = OrderMapper.toDomain(request);

        assertEquals(1, result.getItems().size());
        assertEquals(1, result.getItems().get(0).getMenuItemId());
        assertEquals(2, result.getItems().get(0).getQuantity());
    }

    @Test
    void toDTO_mapsAllFields() {
        OrderItem item = new OrderItem(1, 2, "Burger", 8.99, 1, 8.99, "PENDING");
        Order order = new Order(10, List.of(item), "PENDING", 8.99);

        OrderDTO dto = OrderMapper.toDTO(order);

        assertEquals(10, dto.getId());
        assertEquals("PENDING", dto.getStatus());
        assertEquals(8.99, dto.getTotalPrice());
        assertEquals(1, dto.getItems().size());
    }

    @Test
    void toDTO_mapsItemFieldsCorrectly() {
        OrderItem item = new OrderItem(5, 3, "Cola", 2.50, 2, 5.00, "IN_PROGRESS");
        Order order = new Order(1, List.of(item), "IN_PROGRESS", 5.00);

        OrderDTO dto = OrderMapper.toDTO(order);
        OrderItemDTO itemDto = dto.getItems().get(0);

        assertEquals(5, itemDto.getId());
        assertEquals(3, itemDto.getMenuItemId());
        assertEquals("Cola", itemDto.getMenuItemName());
        assertEquals(2.50, itemDto.getMenuItemPrice());
        assertEquals(2, itemDto.getQuantity());
        assertEquals(5.00, itemDto.getSubtotal());
        assertEquals("IN_PROGRESS", itemDto.getStatus());
    }

    @Test
    void orderItemToDTO_mapsAllFields() {
        OrderItem item = new OrderItem(7, 4, "Pizza", 12.00, 1, 12.00, "DONE");

        OrderItemDTO dto = OrderMapper.orderItemToDTO(item);

        assertEquals(7, dto.getId());
        assertEquals(4, dto.getMenuItemId());
        assertEquals("Pizza", dto.getMenuItemName());
        assertEquals(12.00, dto.getMenuItemPrice());
        assertEquals(1, dto.getQuantity());
        assertEquals(12.00, dto.getSubtotal());
        assertEquals("DONE", dto.getStatus());
    }

    @Test
    void toDTO_withEmptyItemsList_returnsEmptyItems() {
        Order order = new Order(1, List.of(), "PENDING", 0.0);

        OrderDTO dto = OrderMapper.toDTO(order);

        assertTrue(dto.getItems().isEmpty());
    }
}
