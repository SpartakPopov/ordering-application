package com.example.ordering_app.controller;

import com.example.ordering_app.business.OrderService;
import com.example.ordering_app.controller.dto.CreateOrderRequest;
import com.example.ordering_app.controller.dto.OrderDTO;
import com.example.ordering_app.controller.dto.OrderItemDTO;
import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order sampleOrder() {
        OrderItem item = new OrderItem(1, 2, "Burger", 8.99, 1, 8.99, "PENDING");
        return new Order(1, List.of(item), "PENDING", 8.99);
    }

    @Test
    void getAllOrders_returns200WithList() {
        when(orderService.getAllOrders()).thenReturn(List.of(sampleOrder()));

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getId());
    }

    @Test
    void getAllOrders_whenEmpty_returnsEmptyList() {
        when(orderService.getAllOrders()).thenReturn(List.of());

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getOrderById_whenExists_returns200() {
        when(orderService.getOrderById(1)).thenReturn(Optional.of(sampleOrder()));

        ResponseEntity<OrderDTO> response = orderController.getOrderById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void getOrderById_whenNotExists_returns404() {
        when(orderService.getOrderById(999)).thenReturn(Optional.empty());

        ResponseEntity<OrderDTO> response = orderController.getOrderById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createOrder_validRequest_returns200() {
        when(orderService.createOrder(any())).thenReturn(sampleOrder());

        CreateOrderRequest.OrderItemRequest itemReq = new CreateOrderRequest.OrderItemRequest();
        itemReq.setMenuItemId(2);
        itemReq.setQuantity(1);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setItems(List.of(itemReq));

        ResponseEntity<OrderDTO> response = orderController.createOrder(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("PENDING", response.getBody().getStatus());
    }

    @Test
    void startOrder_whenExists_returns200() {
        when(orderService.startOrder(1)).thenReturn(Optional.of(sampleOrder()));

        ResponseEntity<OrderDTO> response = orderController.startOrder(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void startOrder_whenNotExists_returns404() {
        when(orderService.startOrder(999)).thenReturn(Optional.empty());

        ResponseEntity<OrderDTO> response = orderController.startOrder(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void cancelOrder_whenExists_returns204() {
        when(orderService.cancelOrder(1)).thenReturn(true);

        ResponseEntity<Void> response = orderController.cancelOrder(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void cancelOrder_whenNotExists_returns404() {
        when(orderService.cancelOrder(999)).thenReturn(false);

        ResponseEntity<Void> response = orderController.cancelOrder(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void markItemDone_whenNotExists_returns404() {
        when(orderService.markItemDone(eq(1), eq(99))).thenReturn(Optional.empty());

        ResponseEntity<OrderItemDTO> response = orderController.markItemDone(1, 99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void markItemInProgress_whenNotExists_returns404() {
        when(orderService.markItemInProgress(eq(1), eq(99))).thenReturn(Optional.empty());

        ResponseEntity<OrderItemDTO> response = orderController.markItemInProgress(1, 99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
