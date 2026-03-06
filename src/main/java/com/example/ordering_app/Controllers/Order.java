package com.example.ordering_app.Controllers;

public class Order {
    private int id;
    private String name;
    private String type;

    public Order(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Jackson uses these methods to serialize the object to JSON
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
}

