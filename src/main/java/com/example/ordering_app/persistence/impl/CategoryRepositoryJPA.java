package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepositoryJPA extends JpaRepository<CategoryEntity, Integer> {
}