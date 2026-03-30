package com.example.ordering_app.business.exception;

public class MenuItemAlreadyExistsException extends RuntimeException {

    public MenuItemAlreadyExistsException(String message) {
        super(message);
    }
}