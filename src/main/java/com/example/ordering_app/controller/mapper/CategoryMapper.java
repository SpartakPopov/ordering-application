package com.example.ordering_app.controller.mapper;

import com.example.ordering_app.controller.dto.*;
import com.example.ordering_app.domain.Category;
import com.example.ordering_app.domain.MenuItem;

import java.util.List;
import java.util.stream.Collectors;


public class CategoryMapper {

    public static Category toDomain(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }

    public static CreateCategoryResponse toCreateResponse(Category category) {
        return new CreateCategoryResponse(category.getId(), category.getName());
    }

    public static CategoryDTO toDTO(Category category) {
        List<MenuItemDTO> itemDTOs = category.getMenuItems() != null
                ? category.getMenuItems().stream()
                .map(CategoryMapper::menuItemToDTO)
                .collect(Collectors.toList())
                : List.of();

        return new CategoryDTO(category.getId(), category.getName(), itemDTOs);
    }

    private static MenuItemDTO menuItemToDTO(MenuItem item) {
        return new MenuItemDTO(item.getId(), item.getName(), item.getPrice(), item.getCategoryId());
    }
}