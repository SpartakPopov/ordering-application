package com.example.ordering_app.persistence;

import com.example.ordering_app.domain.MenuItem;

import java.util.List;
import java.util.Optional;


public interface MenuItemRepository {

    List<MenuItem> findAll();

    Optional<MenuItem> findById(int id);

    List<MenuItem> findByCategoryId(int categoryId);

    MenuItem save(MenuItem menuItem);

    boolean existsById(int id);

    void deleteById(int id);
}