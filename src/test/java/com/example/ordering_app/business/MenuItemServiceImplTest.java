package com.example.ordering_app.business;

import com.example.ordering_app.domain.MenuItem;
import com.example.ordering_app.persistence.MenuItemRepository;
import com.example.ordering_app.business.impl.MenuItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @Test
    void getAllMenuItems_returnsAllItems() {
        List<MenuItem> fakeItems = List.of(
                new MenuItem(1, "Cola", 2.50, 1),
                new MenuItem(2, "Burger", 8.99, 2)
        );
        when(menuItemRepository.findAll()).thenReturn(fakeItems);

        List<MenuItem> result = menuItemService.getAllMenuItems();

        assertEquals(2, result.size());
        assertEquals("Cola", result.get(0).getName());
        assertEquals("Burger", result.get(1).getName());
        verify(menuItemRepository).findAll();
    }

    @Test
    void getAllMenuItems_whenEmpty_returnsEmptyList() {
        when(menuItemRepository.findAll()).thenReturn(List.of());

        List<MenuItem> result = menuItemService.getAllMenuItems();

        assertTrue(result.isEmpty());
        verify(menuItemRepository).findAll();
    }
}