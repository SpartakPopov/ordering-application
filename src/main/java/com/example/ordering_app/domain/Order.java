package com.example.ordering_app.domain;

import java.util.List;


public class Order {

    private Integer id;
    private List<OrderItem> items;
    private String status; // e.g. PENDING, CONFIRMED, CANCELLED
    private Double totalPrice;

    public Order() {}

    public Order(Integer id, List<OrderItem> items, String status, Double totalPrice) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}