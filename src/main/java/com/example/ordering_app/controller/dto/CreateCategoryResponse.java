package com.example.ordering_app.controller.dto;


public class CreateCategoryResponse {

    private Integer id;
    private String name;

    public CreateCategoryResponse() {}

    public CreateCategoryResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}