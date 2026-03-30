package com.example.ordering_app.controller;

import com.example.ordering_app.business.CategoryService;
import com.example.ordering_app.controller.dto.CategoryDTO;
import com.example.ordering_app.controller.dto.CreateCategoryRequest;
import com.example.ordering_app.controller.dto.CreateCategoryResponse;
import com.example.ordering_app.controller.mapper.CategoryMapper;
import com.example.ordering_app.domain.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> dtos = categoryService.getAllCategories().stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id)
                .map(CategoryMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreateCategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        Category domain = CategoryMapper.toDomain(request);
        Category saved = categoryService.createCategory(domain);
        return ResponseEntity.ok(CategoryMapper.toCreateResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}