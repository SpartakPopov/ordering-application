package com.example.ordering_app.websocket;

import com.example.ordering_app.domain.Order;
import com.example.ordering_app.domain.OrderItem;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class KitchenWebSocketService {

    private static final String KITCHEN_TOPIC = "/topic/kitchen";
    private static final String KEY_PAYLOAD = "payload";
    private static final String KEY_ORDER_ID = "orderId";

    private final SimpMessagingTemplate messagingTemplate;

    public KitchenWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    public void broadcastNewOrder(Order order) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "NEW_ORDER");
        message.put(KEY_PAYLOAD, order);
        messagingTemplate.convertAndSend(KITCHEN_TOPIC, (Object) message); // push message to all subscribed to /topic/kitchen
    }


    public void broadcastItemDone(int orderId, OrderItem item) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "ITEM_DONE");
        Map<String, Object> payload = new HashMap<>();
        payload.put(KEY_ORDER_ID, orderId);
        payload.put("item", item);
        message.put(KEY_PAYLOAD, payload);
        messagingTemplate.convertAndSend(KITCHEN_TOPIC, (Object) message);
    }


    public void broadcastItemInProgress(int orderId, OrderItem item) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "ITEM_IN_PROGRESS");
        Map<String, Object> payload = new HashMap<>();
        payload.put(KEY_ORDER_ID, orderId);
        payload.put("item", item);
        message.put(KEY_PAYLOAD, payload);
        messagingTemplate.convertAndSend(KITCHEN_TOPIC, (Object) message);
    }

    public void broadcastOrderInProgress(int orderId) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "ORDER_IN_PROGRESS");
        Map<String, Object> payload = new HashMap<>();
        payload.put(KEY_ORDER_ID, orderId);
        message.put(KEY_PAYLOAD, payload);
        messagingTemplate.convertAndSend(KITCHEN_TOPIC, (Object) message);
    }

    public void broadcastOrderCompleted(int orderId) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "ORDER_COMPLETED");
        Map<String, Object> payload = new HashMap<>();
        payload.put(KEY_ORDER_ID, orderId);
        message.put(KEY_PAYLOAD, payload);
        messagingTemplate.convertAndSend(KITCHEN_TOPIC, (Object) message);
    }
}