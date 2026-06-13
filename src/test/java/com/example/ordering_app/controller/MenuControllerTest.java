package com.example.ordering_app.controller;

import com.example.ordering_app.business.MenuItemService;
import com.example.ordering_app.controller.dto.CreateMenuItemRequest;
import com.example.ordering_app.controller.dto.CreateMenuItemResponse;
import com.example.ordering_app.controller.dto.MenuItemDTO;
import com.example.ordering_app.domain.MenuItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuController menuController;

    private MenuItem sampleItem() {
        MenuItem item = new MenuItem(1, "Burger", 8.99, 2);
        item.setImageUrl("burger.jpg");
        return item;
    }

    @Test
    void getAllMenuItems_returns200WithList() {
        when(menuItemService.getAllMenuItems()).thenReturn(List.of(sampleItem()));

        ResponseEntity<List<MenuItemDTO>> response = menuController.getAllMenuItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Burger", response.getBody().get(0).getName());
    }

    @Test
    void getAllMenuItems_whenEmpty_returnsEmptyList() {
        when(menuItemService.getAllMenuItems()).thenReturn(List.of());

        ResponseEntity<List<MenuItemDTO>> response = menuController.getAllMenuItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getMenuItemById_whenExists_returns200() {
        when(menuItemService.getMenuItemById(1)).thenReturn(Optional.of(sampleItem()));

        ResponseEntity<MenuItemDTO> response = menuController.getMenuItemById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void getMenuItemById_whenNotExists_returns404() {
        when(menuItemService.getMenuItemById(999)).thenReturn(Optional.empty());

        ResponseEntity<MenuItemDTO> response = menuController.getMenuItemById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getMenuItemsByCategory_returns200WithList() {
        when(menuItemService.getMenuItemsByCategoryId(2)).thenReturn(List.of(sampleItem()));

        ResponseEntity<List<MenuItemDTO>> response = menuController.getMenuItemsByCategory(2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createMenuItem_validRequest_returns200() {
        when(menuItemService.createMenuItem(any())).thenReturn(sampleItem());

        CreateMenuItemRequest request = new CreateMenuItemRequest();
        request.setName("Burger");
        request.setPrice(8.99);
        request.setCategoryId(2);
        request.setImageUrl("burger.jpg");

        ResponseEntity<CreateMenuItemResponse> response = menuController.createMenuItem(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Burger", response.getBody().getName());
    }

    @Test
    void deleteMenuItem_whenExists_returns204() {
        when(menuItemService.deleteMenuItem(1)).thenReturn(true);

        ResponseEntity<Void> response = menuController.deleteMenuItem(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteMenuItem_whenNotExists_returns404() {
        when(menuItemService.deleteMenuItem(999)).thenReturn(false);

        ResponseEntity<Void> response = menuController.deleteMenuItem(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void patchMenuItem_whenExists_returns200() {
        when(menuItemService.patchMenuItem(eq(1), any())).thenReturn(Optional.of(sampleItem()));

        MenuItemDTO dto = new MenuItemDTO(1, "Updated Burger", 9.99, 2, "burger.jpg");

        ResponseEntity<MenuItemDTO> response = menuController.patchMenuItem(1, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void patchMenuItem_whenNotExists_returns404() {
        when(menuItemService.patchMenuItem(eq(999), any())).thenReturn(Optional.empty());

        MenuItemDTO dto = new MenuItemDTO(999, "Ghost", 1.0, 1, "");

        ResponseEntity<MenuItemDTO> response = menuController.patchMenuItem(999, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
