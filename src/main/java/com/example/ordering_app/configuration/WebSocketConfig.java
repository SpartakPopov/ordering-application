package com.example.ordering_app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // creates a sorting for the backend
        // when a message is sent to any destination starting with /topic, the broker recieves it
        // and forwards it to any client that is subscribed to that destination
        config.setApplicationDestinationPrefixes("/app"); 
        // (not used) this is for messages going the opposite direction in case of 
        // implementation in which the kitchen staff need to notify others over WebSockets
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The WebSocket handshake endpoint — clients connect here
        // withSockJS() provides fallback for browsers that don't support WebSocket
        registry.addEndpoint("/ws")             // HTTP handshake URL
                .setAllowedOriginPatterns("*")  // Allowing all origins for now (unsafe)
                .withSockJS();                   // enable fallback 
    }
}