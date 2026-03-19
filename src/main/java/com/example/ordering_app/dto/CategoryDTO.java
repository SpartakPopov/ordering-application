package com.example.ordering_app.dto;

import java.util.List;

public class CategoryDTO {
    private int id;
    private String name;
    private List<MenuItemDTO> menuItems;

    public CategoryDTO() {}

    public CategoryDTO(int id, String name, List<MenuItemDTO> menuItems) {
        this.id = id;
        this.name = name;
        this.menuItems = menuItems;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<MenuItemDTO> getMenuItems() { return menuItems; }
    public void setMenuItems(List<MenuItemDTO> menuItems) { this.menuItems = menuItems; }
}