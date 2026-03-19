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
    // MENU MANAGEMENT
    // Get /api/menu all items
    // Get /api/menu/{id} filter by id
    // Get /api/menu/{type} filter by type
    // Post /api/menu  Create item (admin)
    // Put /api/menu/{id} Update item (admin)
    // Delete /api/menu/{id} Delete item (admin)
    // Get /api/cathegoties/{id} filter cathegory by id
    // Post /api/cathegories craete cathegory
    // Get /api/cathegories/ all cathegories
    // Patch /api/menu/{id}
    //
    // ORDER MANAGEMENT
    // Phases of order: Order sent --> Order prepared -->Order served
    // Post /api/orders/
    // Get /api/orders/{id}
    // Get /api/orders/session/{session_id}
    // Post /api/orders/{id}/items
    // Delete /api/orders/{id}/items/{item_id}
    // Patch /api/orders/{id}status
    //
    // KITCHEN DISPLAY
    // Get /api/kitchen/queue
    // Patch /api/kitchen/orders/{id}/prepared
    // Get /api/kitchen/history
    //
    // TABLE MANAGEMENT
    // Get /api/table
    // Post /api/table Add a table (admin)
    // Patch /api/table/{id} Update status (admin)
    // Delete /api/table/{id} Remove table (admin)
    //
    // TABLE SESSION
    // Post /api/sessiom/activate/{table_id}
    // Get /api/sessions/validate/{table_id}
    // Post /api/sessions/close/{table_id}
    //
    // PAYMENTS
    // Post /api/payments
    // Post /api/payments/{id}/tip
    // Post /api/payments/{id}/confirm
    // Get /api/payments/session/{sessionId}

}