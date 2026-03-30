package com.example.ordering_app.persistence.entity;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MenuItemEntity> menuItems;

    public CategoryEntity() {}

    public CategoryEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<MenuItemEntity> getMenuItems() { return menuItems; }
    public void setMenuItems(List<MenuItemEntity> menuItems) { this.menuItems = menuItems; }
}