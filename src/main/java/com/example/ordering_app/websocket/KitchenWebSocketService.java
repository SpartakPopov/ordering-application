package com.example.ordering_app.websocket;

import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class KitchenWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public KitchenWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    public void broadcastNewOrder(Order order) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "NEW_ORDER");
        message.put("payload", order);
        messagingTemplate.convertAndSend("/topic/kitchen", (Object) message);
    }


    public void broadcastItemDone(int orderId, OrderItem item) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "ITEM_DONE");
        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", orderId);
        payload.put("item", item);
        message.put("payload", payload);
        messagingTemplate.convertAndSend("/topic/kitchen", (Object) message);
    }


    public void broadcastOrderCompleted(int orderId) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "ORDER_COMPLETED");
        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", orderId);
        message.put("payload", payload);
        messagingTemplate.convertAndSend("/topic/kitchen", (Object) message);
    }
}