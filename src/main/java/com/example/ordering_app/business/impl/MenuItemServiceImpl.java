package com.example.ordering_app.business.impl;

import com.example.ordering_app.business.MenuItemService;
import com.example.ordering_app.domain.MenuItem;
import com.example.ordering_app.persistence.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public Optional<MenuItem> getMenuItemById(int id) {
        return menuItemRepository.findById(id);
    }

    @Override
    public List<MenuItem> getMenuItemsByCategoryId(int categoryId) {
        return menuItemRepository.findByCategoryId(categoryId);
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public boolean deleteMenuItem(int id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MenuItem> patchMenuItem(int id, MenuItem menuItem) {
        return menuItemRepository.findById(id).map(existing -> {
            if (menuItem.getName() != null) existing.setName(menuItem.getName());
            if (menuItem.getPrice() != null) existing.setPrice(menuItem.getPrice());
            return menuItemRepository.save(existing);
        });
    }
}