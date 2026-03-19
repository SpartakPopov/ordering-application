package com.example.ordering_app.Service;

import com.example.ordering_app.dto.CategoryDTO;
import com.example.ordering_app.dto.MenuItemDTO;
import com.example.ordering_app.models.Category;
import com.example.ordering_app.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(int id) {
        return categoryRepository.findById(id).map(this::toDTO);
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        Category saved = categoryRepository.save(category);
        return new CategoryDTO(saved.getId(), saved.getName(), List.of());
    }

    private CategoryDTO toDTO(Category category) {
        List<MenuItemDTO> items = category.getMenuItems() != null
                ? category.getMenuItems().stream()
                .map(item -> new MenuItemDTO(item.getId(), item.getName(),
                        item.getPrice(), category.getId()))
                .collect(Collectors.toList())
                : List.of();

        return new CategoryDTO(category.getId(), category.getName(), items);
    }

    public boolean deleteCategory(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}