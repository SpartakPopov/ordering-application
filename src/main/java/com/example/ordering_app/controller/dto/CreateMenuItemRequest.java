package com.example.ordering_app.controller.dto;


public class CreateMenuItemRequest {

    private String name;
    private Double price;
    private Integer categoryId;
    private String imageUrl;

    public CreateMenuItemRequest() {}

    public CreateMenuItemRequest(String name, Double price, Integer categoryId, String imageUrl) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}