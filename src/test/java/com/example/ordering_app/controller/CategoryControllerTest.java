package com.example.ordering_app.controller;

import com.example.ordering_app.business.CategoryService;
import com.example.ordering_app.controller.dto.CategoryDTO;
import com.example.ordering_app.controller.dto.CreateCategoryRequest;
import com.example.ordering_app.controller.dto.CreateCategoryResponse;
import com.example.ordering_app.domain.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void getAllCategories_returns200WithList() {
        when(categoryService.getAllCategories()).thenReturn(List.of(
                new Category(1, "Drinks"),
                new Category(2, "Food")
        ));

        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Drinks", response.getBody().get(0).getName());
    }

    @Test
    void getAllCategories_whenEmpty_returnsEmptyList() {
        when(categoryService.getAllCategories()).thenReturn(List.of());

        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getCategoryById_whenExists_returns200() {
        when(categoryService.getCategoryById(1)).thenReturn(Optional.of(new Category(1, "Drinks", List.of())));

        ResponseEntity<CategoryDTO> response = categoryController.getCategoryById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("Drinks", response.getBody().getName());
    }

    @Test
    void getCategoryById_whenNotExists_returns404() {
        when(categoryService.getCategoryById(999)).thenReturn(Optional.empty());

        ResponseEntity<CategoryDTO> response = categoryController.getCategoryById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createCategory_validRequest_returns200() {
        when(categoryService.createCategory(any())).thenReturn(new Category(3, "Desserts"));

        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Desserts");

        ResponseEntity<CreateCategoryResponse> response = categoryController.createCategory(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Desserts", response.getBody().getName());
    }

    @Test
    void deleteCategory_whenExists_returns204() {
        when(categoryService.deleteCategory(1)).thenReturn(true);

        ResponseEntity<Void> response = categoryController.deleteCategory(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteCategory_whenNotExists_returns404() {
        when(categoryService.deleteCategory(999)).thenReturn(false);

        ResponseEntity<Void> response = categoryController.deleteCategory(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
