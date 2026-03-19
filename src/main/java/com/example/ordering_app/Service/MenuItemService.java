package com.example.ordering_app.Service;

import com.example.ordering_app.dto.MenuItemDTO;
import com.example.ordering_app.models.MenuItem;
import com.example.ordering_app.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;
import com.example.ordering_app.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItemDTO createMenuItem(MenuItemDTO dto) {
        MenuItem item = new MenuItem();
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            item.setCategory(category);
        }
        MenuItem saved = menuItemRepository.save(item);
        return toDTO(saved);
    }

    public boolean deleteMenuItem(int id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> getMenuItemsByCategoryId(int categoryId) {
        return menuItemRepository.findByCategoryId(categoryId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MenuItemDTO> getMenuItemById(int id) {
        return menuItemRepository.findById(id).map(this::toDTO);
    }

    public Optional<MenuItemDTO> patchMenuItem(int id, MenuItemDTO dto) {
        return menuItemRepository.findById(id).map(item -> {
            if (dto.getName() != null) item.setName(dto.getName());
            if (dto.getPrice() != null) item.setPrice(dto.getPrice());
            return toDTO(menuItemRepository.save(item));
        });
    }

    private MenuItemDTO toDTO(MenuItem item) {
        Integer categoryId = item.getCategory() != null ? item.getCategory().getId() : null;
        return new MenuItemDTO(item.getId(), item.getName(), item.getPrice(), categoryId);
    }


}