package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.domain.Category;
import com.example.ordering_app.domain.MenuItem;
import com.example.ordering_app.persistence.CategoryRepository;
import com.example.ordering_app.persistence.entity.CategoryEntity;
import com.example.ordering_app.persistence.entity.MenuItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryRepositoryJPA categoryRepositoryJPA;

    public CategoryRepositoryImpl(CategoryRepositoryJPA categoryRepositoryJPA) {
        this.categoryRepositoryJPA = categoryRepositoryJPA;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepositoryJPA.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepositoryJPA.findById(id).map(this::toDomain);
    }

    @Override
    public Category save(Category category) {
        CategoryEntity entity = toEntity(category);
        CategoryEntity saved = categoryRepositoryJPA.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(int id) {
        return categoryRepositoryJPA.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        categoryRepositoryJPA.deleteById(id);
    }

    // --- Conversion methods: Entity <-> Domain ---

    private Category toDomain(CategoryEntity entity) {
        List<MenuItem> items = entity.getMenuItems() != null
                ? entity.getMenuItems().stream()
                .map(this::menuItemToDomain)
                .collect(Collectors.toList())
                : List.of();

        return new Category(entity.getId(), entity.getName(), items);
    }

    private MenuItem menuItemToDomain(MenuItemEntity entity) {
        Integer categoryId = entity.getCategory() != null ? entity.getCategory().getId() : null;
        return new MenuItem(entity.getId(), entity.getName(), entity.getPrice(), categoryId);
    }

    private CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        return entity;
    }
}