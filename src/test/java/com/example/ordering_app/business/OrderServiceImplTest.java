package com.example.ordering_app.business;

import com.example.ordering_app.business.impl.OrderServiceImpl;
import com.example.ordering_app.domain.MenuItem;
import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;
import com.example.ordering_app.persistence.MenuItemRepository;
import com.example.ordering_app.persistence.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;



    @Test
    void getAllOrders_returnsAllOrders() {
        List<Order> fakeOrders = List.of(
                new Order(1, List.of(), "PENDING", 10.00),
                new Order(2, List.of(), "PENDING", 20.00)
        );
        when(orderRepository.findAll()).thenReturn(fakeOrders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getAllOrders_whenEmpty_returnsEmptyList() {
        when(orderRepository.findAll()).thenReturn(List.of());

        List<Order> result = orderService.getAllOrders();

        assertTrue(result.isEmpty());
        verify(orderRepository).findAll();
    }



    @Test
    void getOrderById_whenExists_returnsOrder() {
        Order fakeOrder = new Order(1, List.of(), "PENDING", 10.00);
        when(orderRepository.findById(1)).thenReturn(Optional.of(fakeOrder));

        Optional<Order> result = orderService.getOrderById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        assertEquals("PENDING", result.get().getStatus());
    }

    @Test
    void getOrderById_whenNotExists_returnsEmpty() {
        when(orderRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(999);

        assertTrue(result.isEmpty());
    }



    @Test
    void createOrder_calculatesSubtotalAndTotalCorrectly() {
        // Cola x2 = 5.00, Burger x1 = 8.99 → total = 13.99
        OrderItem colaItem = new OrderItem();
        colaItem.setMenuItemId(1);
        colaItem.setQuantity(2);

        OrderItem burgerItem = new OrderItem();
        burgerItem.setMenuItemId(2);
        burgerItem.setQuantity(1);

        Order order = new Order();
        order.setItems(new ArrayList<>(List.of(colaItem, burgerItem)));

        MenuItem cola = new MenuItem(1, "Cola", 2.50, 1);
        MenuItem burger = new MenuItem(2, "Burger", 8.99, 2);

        when(menuItemRepository.findById(1)).thenReturn(Optional.of(cola));
        when(menuItemRepository.findById(2)).thenReturn(Optional.of(burger));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        Order result = orderService.createOrder(order);

        assertEquals("PENDING", result.getStatus());
        assertEquals(13.99, result.getTotalPrice(), 0.01);
        assertEquals(5.00, result.getItems().get(0).getSubtotal(), 0.01);
        assertEquals(8.99, result.getItems().get(1).getSubtotal(), 0.01);
    }

    @Test
    void createOrder_setsMenuItemNameAndPriceFromDatabase() {
        OrderItem item = new OrderItem();
        item.setMenuItemId(1);
        item.setQuantity(1);

        Order order = new Order();
        order.setItems(new ArrayList<>(List.of(item)));

        MenuItem cola = new MenuItem(1, "Cola", 2.50, 1);
        when(menuItemRepository.findById(1)).thenReturn(Optional.of(cola));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        Order result = orderService.createOrder(order);

        assertEquals("Cola", result.getItems().get(0).getMenuItemName());
        assertEquals(2.50, result.getItems().get(0).getMenuItemPrice());
    }

    @Test
    void createOrder_whenEmptyItems_throwsException() {
        Order order = new Order();
        order.setItems(List.of());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(order)
        );
        assertEquals("An order must contain at least one item", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_whenNullItems_throwsException() {
        Order order = new Order();
        order.setItems(null);

        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(order));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_whenQuantityIsZero_throwsException() {
        OrderItem item = new OrderItem();
        item.setMenuItemId(1);
        item.setQuantity(0);

        Order order = new Order();
        order.setItems(new ArrayList<>(List.of(item)));

        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(order));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_whenMenuItemNotFound_throwsException() {
        OrderItem item = new OrderItem();
        item.setMenuItemId(999);
        item.setQuantity(1);

        Order order = new Order();
        order.setItems(new ArrayList<>(List.of(item)));

        when(menuItemRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(order));
        verify(orderRepository, never()).save(any());
    }



    @Test
    void cancelOrder_whenExists_deletesAndReturnsTrue() {
        when(orderRepository.existsById(1)).thenReturn(true);

        boolean result = orderService.cancelOrder(1);

        assertTrue(result);
        verify(orderRepository).deleteById(1);
    }

    @Test
    void cancelOrder_whenNotExists_returnsFalseAndDoesNotDelete() {
        when(orderRepository.existsById(999)).thenReturn(false);

        boolean result = orderService.cancelOrder(999);

        assertFalse(result);
        verify(orderRepository, never()).deleteById(999);
    }
}