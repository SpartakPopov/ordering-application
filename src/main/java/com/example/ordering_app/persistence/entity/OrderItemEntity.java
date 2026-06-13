package com.example.ordering_app.persistence.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer menuItemId;
    private String menuItemName;
    private Double menuItemPrice;
    private Integer quantity;
    private Double subtotal;
    private String status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public OrderItemEntity() { /* required by JPA */ }

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
    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }
}