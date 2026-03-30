package com.example.ordering_app.business;

import com.example.ordering_app.domain.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryService {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(int id);

    Category createCategory(Category category);

    boolean deleteCategory(int id);
}