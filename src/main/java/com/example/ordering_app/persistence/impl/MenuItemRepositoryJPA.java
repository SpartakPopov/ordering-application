package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.persistence.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuItemRepositoryJPA extends JpaRepository<MenuItemEntity, Integer> {
    List<MenuItemEntity> findByCategoryId(int categoryId);
}