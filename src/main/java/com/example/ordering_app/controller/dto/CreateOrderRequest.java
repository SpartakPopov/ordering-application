package com.example.ordering_app.controller.dto;

import java.util.List;


public class CreateOrderRequest {

    private List<OrderItemRequest> items;

    public CreateOrderRequest() { /* required by Jackson for JSON deserialisation */ }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }

    public static class OrderItemRequest {
        private Integer menuItemId;
        private Integer quantity;

        public OrderItemRequest() { /* required by Jackson for JSON deserialisation */ }

        public Integer getMenuItemId() { return menuItemId; }
        public void setMenuItemId(Integer menuItemId) { this.menuItemId = menuItemId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}