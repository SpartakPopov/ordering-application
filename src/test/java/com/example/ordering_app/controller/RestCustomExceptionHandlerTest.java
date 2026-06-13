package com.example.ordering_app.controller;

import com.example.ordering_app.business.exception.CategoryAlreadyExistsException;
import com.example.ordering_app.business.exception.MenuItemAlreadyExistsException;
import com.example.ordering_app.configuration.exceptionhandler.RestCustomExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestCustomExceptionHandlerTest {

    private final RestCustomExceptionHandler handler = new RestCustomExceptionHandler();

    @Test
    void categoryAlreadyExists_returns409WithMessage() {
        CategoryAlreadyExistsException ex = new CategoryAlreadyExistsException("Drinks already exists");

        ResponseEntity<Map<String, String>> response = handler.handleCategoryAlreadyExists(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Drinks already exists", response.getBody().get("error"));
    }

    @Test
    void menuItemAlreadyExists_returns409WithMessage() {
        MenuItemAlreadyExistsException ex = new MenuItemAlreadyExistsException("Burger already exists");

        ResponseEntity<Map<String, String>> response = handler.handleMenuItemAlreadyExists(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Burger already exists", response.getBody().get("error"));
    }

    @Test
    void genericException_returns500WithGenericMessage() {
        Exception ex = new RuntimeException("Something went wrong");

        ResponseEntity<Map<String, String>> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody().get("error"));
    }
}
