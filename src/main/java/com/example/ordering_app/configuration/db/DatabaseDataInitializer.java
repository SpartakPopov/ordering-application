package com.example.ordering_app.configuration.db;

import com.example.ordering_app.persistence.entity.CategoryEntity;
import com.example.ordering_app.persistence.entity.MenuItemEntity;
import com.example.ordering_app.persistence.impl.CategoryRepositoryJPA;
import com.example.ordering_app.persistence.impl.MenuItemRepositoryJPA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DatabaseDataInitializer implements CommandLineRunner {

    private final CategoryRepositoryJPA categoryRepositoryJPA;
    private final MenuItemRepositoryJPA menuItemRepositoryJPA;

    public DatabaseDataInitializer(CategoryRepositoryJPA categoryRepositoryJPA,
                                   MenuItemRepositoryJPA menuItemRepositoryJPA) {
        this.categoryRepositoryJPA = categoryRepositoryJPA;
        this.menuItemRepositoryJPA = menuItemRepositoryJPA;
    }

    @Override
    public void run(String... args) {
        if (categoryRepositoryJPA.count() == 0) {
            CategoryEntity drinks = new CategoryEntity();
            drinks.setName("Drinks");

            CategoryEntity food = new CategoryEntity();
            food.setName("Food");

            categoryRepositoryJPA.saveAll(List.of(drinks, food));

            MenuItemEntity cola = new MenuItemEntity();
            cola.setName("Cola");
            cola.setPrice(2.50);
            cola.setCategory(drinks);

            MenuItemEntity burger = new MenuItemEntity();
            burger.setName("Burger");
            burger.setPrice(8.99);
            burger.setCategory(food);

            menuItemRepositoryJPA.saveAll(List.of(cola, burger));
        }
    }
}