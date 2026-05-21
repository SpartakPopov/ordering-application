package com.example.ordering_app.domain;


public class OrderItem {

    private Integer id;
    private Integer menuItemId;
    private String menuItemName;
    private Double menuItemPrice;
    private Integer quantity;
    private Double subtotal;
    private String status; // PENDING or DONE

    public OrderItem() {}

    public OrderItem(Integer id, Integer menuItemId, String menuItemName,
                     Double menuItemPrice, Integer quantity, Double subtotal, String status) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.menuItemPrice = menuItemPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.status = status;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getMenuItemId() { return menuItemId; }
    public void setMenuItemId(Integer menuItemId) { this.menuItemId = menuItemId; }
    public String getMenuItemName() { return menuItemName; }
    public void setMenuItemName(String menuItemName) { this.menuItemName = menuItemName; }
    public Double getMenuItemPrice() { return menuItemPrice; }
    public void setMenuItemPrice(Double menuItemPrice) { this.menuItemPrice = menuItemPrice; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}