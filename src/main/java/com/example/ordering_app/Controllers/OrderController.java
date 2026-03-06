package com.example.ordering_app.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrderController {


    @GetMapping("order")
    public ResponseEntity<List<Order>> getOrder() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "Chicken soup", "Soup"));
        orders.add(new Order(2, "Steak", "Meat"));
        orders.add(new Order(3, "Bread", "Wheat"));
        return ResponseEntity.ok(orders);
    }
}
