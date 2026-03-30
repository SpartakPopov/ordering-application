package com.example.ordering_app.persistence.impl;

import com.example.ordering_app.domain.MenuItem;
import com.example.ordering_app.persistence.MenuItemRepository;
import com.example.ordering_app.persistence.entity.CategoryEntity;
import com.example.ordering_app.persistence.entity.MenuItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final MenuItemRepositoryJPA menuItemRepositoryJPA;

    public MenuItemRepositoryImpl(MenuItemRepositoryJPA menuItemRepositoryJPA) {
        this.menuItemRepositoryJPA = menuItemRepositoryJPA;
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepositoryJPA.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MenuItem> findById(int id) {
        return menuItemRepositoryJPA.findById(id).map(this::toDomain);
    }

    @Override
    public List<MenuItem> findByCategoryId(int categoryId) {
        return menuItemRepositoryJPA.findByCategoryId(categoryId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity entity = toEntity(menuItem);
        MenuItemEntity saved = menuItemRepositoryJPA.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(int id) {
        return menuItemRepositoryJPA.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        menuItemRepositoryJPA.deleteById(id);
    }

    // --- Conversion methods: Entity <-> Domain ---

    private MenuItem toDomain(MenuItemEntity entity) {
        Integer categoryId = entity.getCategory() != null ? entity.getCategory().getId() : null;
        return new MenuItem(entity.getId(), entity.getName(), entity.getPrice(), categoryId);
    }

    private MenuItemEntity toEntity(MenuItem menuItem) {
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(menuItem.getId());
        entity.setName(menuItem.getName());
        entity.setPrice(menuItem.getPrice());
        if (menuItem.getCategoryId() != null) {
            CategoryEntity category = new CategoryEntity();
            category.setId(menuItem.getCategoryId());
            entity.setCategory(category);
        }
        return entity;
    }
}