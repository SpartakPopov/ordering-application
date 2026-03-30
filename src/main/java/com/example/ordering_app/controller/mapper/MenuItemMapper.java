package com.example.ordering_app.controller.mapper;

import com.example.ordering_app.controller.dto.CreateMenuItemRequest;
import com.example.ordering_app.controller.dto.CreateMenuItemResponse;
import com.example.ordering_app.controller.dto.MenuItemDTO;
import com.example.ordering_app.domain.MenuItem;


public class MenuItemMapper {

    public static MenuItem toDomain(CreateMenuItemRequest request) {
        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setCategoryId(request.getCategoryId());
        return item;
    }

    public static MenuItem toDomain(MenuItemDTO dto) {
        MenuItem item = new MenuItem();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setCategoryId(dto.getCategoryId());
        return item;
    }

    public static CreateMenuItemResponse toCreateResponse(MenuItem item) {
        return new CreateMenuItemResponse(item.getId(), item.getName(), item.getPrice(), item.getCategoryId());
    }

    public static MenuItemDTO toDTO(MenuItem item) {
        return new MenuItemDTO(item.getId(), item.getName(), item.getPrice(), item.getCategoryId());
    }
}