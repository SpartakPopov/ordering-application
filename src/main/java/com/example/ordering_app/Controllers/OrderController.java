package com.example.ordering_app.Controllers;

import com.example.ordering_app.dto.MenuItemDTO;
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
    public ResponseEntity<List<MenuItemDTO>> getOrder() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("menu/category/{categoryId}")
    public ResponseEntity<List<MenuItemDTO>> getOrderByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByCategoryId(categoryId));
    }

    @GetMapping("menu/{id}")
    public ResponseEntity<MenuItemDTO> getOrderById(@PathVariable int id) {
        return menuItemService.getMenuItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("menu")
    public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody MenuItemDTO dto) {
        return ResponseEntity.ok(menuItemService.createMenuItem(dto));
    }

    @DeleteMapping("menu/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable int id) {
        if (menuItemService.deleteMenuItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("menu/{id}")
    public ResponseEntity<MenuItemDTO> patchMenuItem(@PathVariable int id, @RequestBody MenuItemDTO dto) {
        return menuItemService.patchMenuItem(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}