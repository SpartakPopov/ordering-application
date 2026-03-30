package com.example.ordering_app.business;

import com.example.ordering_app.domain.MenuItem;

import java.util.List;
import java.util.Optional;


public interface MenuItemService {

    List<MenuItem> getAllMenuItems();

    Optional<MenuItem> getMenuItemById(int id);

    List<MenuItem> getMenuItemsByCategoryId(int categoryId);

    MenuItem createMenuItem(MenuItem menuItem);

    boolean deleteMenuItem(int id);

    Optional<MenuItem> patchMenuItem(int id, MenuItem menuItem);
}