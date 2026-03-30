package com.example.ordering_app.persistence;

import com.example.ordering_app.domain.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository {

    List<Category> findAll();

    Optional<Category> findById(int id);

    Category save(Category category);

    boolean existsById(int id);

    void deleteById(int id);
}