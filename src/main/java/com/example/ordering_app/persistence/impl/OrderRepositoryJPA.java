package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryJPA extends JpaRepository<OrderEntity, Integer> {
}