package com.example.ordering_app.business;

import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;

import java.util.List;
import java.util.Optional;


public interface OrderService {

    List<Order> getAllOrders();

    Optional<Order> getOrderById(int id);

    Order createOrder(Order order);

    Optional<Order> startOrder(int orderId);

    Optional<OrderItem> markItemInProgress(int orderId, int itemId);

    Optional<OrderItem> markItemDone(int orderId, int itemId);

    boolean cancelOrder(int id);
}