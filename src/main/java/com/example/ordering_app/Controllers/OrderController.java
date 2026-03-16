package com.example.ordering_app.Controllers;

import com.example.ordering_app.models.MenuItem;
import com.example.ordering_app.Service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrderController {

    private final MenuItemService menuItemService;

    public OrderController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("menu")
    public ResponseEntity<List<MenuItem>> getOrder() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("menu/{type}")
    public ResponseEntity<List<MenuItem>> getOrderByType(@PathVariable String type) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByType(type));
    }

    @GetMapping("menu/getOne/{id}")
    public ResponseEntity<MenuItem> getOrderById(@PathVariable int id) {
        return menuItemService.getMenuItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}