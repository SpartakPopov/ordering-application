package com.example.ordering_app.controller.dto;

import java.util.List;

public class OrderDTO {

    private Integer id;
    private List<OrderItemDTO> items;
    private String status;
    private Double totalPrice;

    public OrderDTO() {}

    public OrderDTO(Integer id, List<OrderItemDTO> items, String status, Double totalPrice) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}