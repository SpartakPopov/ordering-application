package com.example.ordering_app.controller.mapper;

import com.example.ordering_app.controller.dto.CategoryDTO;
import com.example.ordering_app.controller.dto.CreateCategoryRequest;
import com.example.ordering_app.controller.dto.CreateCategoryResponse;
import com.example.ordering_app.domain.Category;
import com.example.ordering_app.domain.MenuItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void toDomain_mapsNameCorrectly() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Drinks");

        Category result = CategoryMapper.toDomain(request);

        assertEquals("Drinks", result.getName());
    }

    @Test
    void toCreateResponse_mapsIdAndName() {
        Category category = new Category(3, "Desserts");

        CreateCategoryResponse response = CategoryMapper.toCreateResponse(category);

        assertEquals(3, response.getId());
        assertEquals("Desserts", response.getName());
    }

    @Test
    void toDTO_mapsIdAndName() {
        Category category = new Category(1, "Starters", List.of());

        CategoryDTO dto = CategoryMapper.toDTO(category);

        assertEquals(1, dto.getId());
        assertEquals("Starters", dto.getName());
    }

    @Test
    void toDTO_withMenuItems_mapsItems() {
        MenuItem item = new MenuItem(10, "Spring Roll", 4.50, 1);
        Category category = new Category(1, "Starters", List.of(item));

        CategoryDTO dto = CategoryMapper.toDTO(category);

        assertEquals(1, dto.getMenuItems().size());
        assertEquals("Spring Roll", dto.getMenuItems().get(0).getName());
        assertEquals(4.50, dto.getMenuItems().get(0).getPrice());
    }

    @Test
    void toDTO_withNullMenuItems_returnsEmptyList() {
        Category category = new Category(1, "Starters", null);

        CategoryDTO dto = CategoryMapper.toDTO(category);

        assertNotNull(dto.getMenuItems());
        assertTrue(dto.getMenuItems().isEmpty());
    }
}
