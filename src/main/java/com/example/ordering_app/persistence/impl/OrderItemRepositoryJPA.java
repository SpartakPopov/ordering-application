package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.persistence.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderItemRepositoryJPA extends JpaRepository<OrderItemEntity, Integer> {

}