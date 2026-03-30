package com.example.ordering_app.controller;

import com.example.ordering_app.business.MenuItemService;
import com.example.ordering_app.controller.dto.CreateMenuItemRequest;
import com.example.ordering_app.controller.dto.CreateMenuItemResponse;
import com.example.ordering_app.controller.dto.MenuItemDTO;
import com.example.ordering_app.controller.mapper.MenuItemMapper;
import com.example.ordering_app.domain.MenuItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuItemService menuItemService;

    public MenuController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        List<MenuItemDTO> dtos = menuItemService.getAllMenuItems().stream()
                .map(MenuItemMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByCategory(@PathVariable int categoryId) {
        List<MenuItemDTO> dtos = menuItemService.getMenuItemsByCategoryId(categoryId).stream()
                .map(MenuItemMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable int id) {
        return menuItemService.getMenuItemById(id)
                .map(MenuItemMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreateMenuItemResponse> createMenuItem(@RequestBody CreateMenuItemRequest request) {
        MenuItem domain = MenuItemMapper.toDomain(request);
        MenuItem saved = menuItemService.createMenuItem(domain);
        return ResponseEntity.ok(MenuItemMapper.toCreateResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable int id) {
        if (menuItemService.deleteMenuItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuItemDTO> patchMenuItem(@PathVariable int id, @RequestBody MenuItemDTO dto) {
        MenuItem domain = MenuItemMapper.toDomain(dto);
        return menuItemService.patchMenuItem(id, domain)
                .map(MenuItemMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}