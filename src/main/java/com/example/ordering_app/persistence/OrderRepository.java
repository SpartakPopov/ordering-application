package com.example.ordering_app.persistence;

import com.example.ordering_app.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();

    Optional<Order> findById(int id);

    Order save(Order order);

    boolean existsById(int id);

    void deleteById(int id);
}