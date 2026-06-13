package com.example.ordering_app.controller.mapper;

import com.example.ordering_app.controller.dto.CreateMenuItemRequest;
import com.example.ordering_app.controller.dto.CreateMenuItemResponse;
import com.example.ordering_app.controller.dto.MenuItemDTO;
import com.example.ordering_app.domain.MenuItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemMapperTest {

    @Test
    void toDomain_fromCreateRequest_mapsAllFields() {
        CreateMenuItemRequest request = new CreateMenuItemRequest();
        request.setName("Burger");
        request.setPrice(8.99);
        request.setCategoryId(2);
        request.setImageUrl("burger.jpg");

        MenuItem result = MenuItemMapper.toDomain(request);

        assertEquals("Burger", result.getName());
        assertEquals(8.99, result.getPrice());
        assertEquals(2, result.getCategoryId());
        assertEquals("burger.jpg", result.getImageUrl());
    }

    @Test
    void toDomain_fromDTO_mapsAllFields() {
        MenuItemDTO dto = new MenuItemDTO(5, "Pizza", 12.00, 3, "pizza.jpg");

        MenuItem result = MenuItemMapper.toDomain(dto);

        assertEquals(5, result.getId());
        assertEquals("Pizza", result.getName());
        assertEquals(12.00, result.getPrice());
        assertEquals(3, result.getCategoryId());
        assertEquals("pizza.jpg", result.getImageUrl());
    }

    @Test
    void toCreateResponse_mapsAllFields() {
        MenuItem item = new MenuItem(1, "Cola", 2.50, 1);
        item.setImageUrl("cola.jpg");

        CreateMenuItemResponse response = MenuItemMapper.toCreateResponse(item);

        assertEquals(1, response.getId());
        assertEquals("Cola", response.getName());
        assertEquals(2.50, response.getPrice());
        assertEquals(1, response.getCategoryId());
        assertEquals("cola.jpg", response.getImageUrl());
    }

    @Test
    void toDTO_mapsAllFields() {
        MenuItem item = new MenuItem(7, "Salad", 6.50, 2);
        item.setImageUrl("salad.jpg");

        MenuItemDTO dto = MenuItemMapper.toDTO(item);

        assertEquals(7, dto.getId());
        assertEquals("Salad", dto.getName());
        assertEquals(6.50, dto.getPrice());
        assertEquals(2, dto.getCategoryId());
        assertEquals("salad.jpg", dto.getImageUrl());
    }
}
