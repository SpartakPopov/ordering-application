package com.example.ordering_app.business;

import com.example.ordering_app.domain.Category;
import com.example.ordering_app.persistence.CategoryRepository;
import com.example.ordering_app.business.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getAllCategories_returnsAllCategories() {
        List<Category> fakeCategories = List.of(
                new Category(1, "Drinks"),
                new Category(2, "Food")
        );
        when(categoryRepository.findAll()).thenReturn(fakeCategories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Drinks", result.get(0).getName());
        assertEquals("Food", result.get(1).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void getAllCategories_whenEmpty_returnsEmptyList() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<Category> result = categoryService.getAllCategories();

        assertTrue(result.isEmpty());
        verify(categoryRepository).findAll();
    }
}