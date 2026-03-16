package com.example.ordering_app.Service;

import com.example.ordering_app.models.MenuItem;
import com.example.ordering_app.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByType(String type) {
        return menuItemRepository.findByType(type);
    }

    public Optional<MenuItem> getMenuItemById(int id) {
        return menuItemRepository.findById(id);
    }
}