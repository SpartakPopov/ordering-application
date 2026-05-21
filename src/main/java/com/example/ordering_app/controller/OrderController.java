package com.example.ordering_app.controller;

import com.example.ordering_app.business.OrderService;
import com.example.ordering_app.controller.dto.CreateOrderRequest;
import com.example.ordering_app.controller.dto.OrderDTO;
import com.example.ordering_app.controller.dto.OrderItemDTO;
import com.example.ordering_app.controller.mapper.OrderMapper;
import com.example.ordering_app.domain.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> dtos = orderService.getAllOrders().stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id)
                .map(OrderMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        Order domain = OrderMapper.toDomain(request);
        Order saved = orderService.createOrder(domain);
        return ResponseEntity.ok(OrderMapper.toDTO(saved));
    }


    @PatchMapping("/{orderId}/items/{itemId}/done")
    public ResponseEntity<OrderItemDTO> markItemDone(
            @PathVariable int orderId,
            @PathVariable int itemId) {
        return orderService.markItemDone(orderId, itemId)
                .map(OrderMapper::orderItemToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable int id) {
        if (orderService.cancelOrder(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}