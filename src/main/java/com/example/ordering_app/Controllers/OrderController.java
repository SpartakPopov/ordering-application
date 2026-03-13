package com.example.ordering_app.Controllers;

import com.example.ordering_app.models.MenuItem;
import com.example.ordering_app.repositories.MenuItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrderController {

    private final MenuItemRepository menuItemRepository;

    public OrderController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping("menu")
    public ResponseEntity<List<MenuItem>> getOrder() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        return ResponseEntity.ok(menuItems);
    }
    @GetMapping("menu/{type}")
    public ResponseEntity<List<MenuItem>> getOrderByType(@PathVariable String type) {
        List<MenuItem> menuItems = menuItemRepository.findByType(type);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("menu/getOne/{id}")
    public ResponseEntity<MenuItem> getOrderById(@PathVariable int id) {
        return menuItemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
