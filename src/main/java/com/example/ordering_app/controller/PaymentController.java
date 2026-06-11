package com.example.ordering_app.controller;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @Value("${stripe.publishable-key}")
    private String publishableKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    // Returns the publishable key so the frontend can initialize Stripe.js
    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> getConfig() {
        return ResponseEntity.ok(Map.of("publishableKey", publishableKey));
    }

    // Creates a PaymentIntent for the given amount and returns the client_secret
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(
            @RequestBody Map<String, Object> body) throws StripeException {

        long amount = Long.parseLong(body.get("amount").toString()); // amount in cents

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("eur")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return ResponseEntity.ok(Map.of("clientSecret", paymentIntent.getClientSecret()));
    }

    // Stripe calls this endpoint after a payment event — signature must be verified
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            // Invalid signature — reject the request
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
                // TODO: mark the linked order as PAID in the database
                System.out.println("Payment succeeded: " + event.getId());
                break;
            case "payment_intent.payment_failed":
                System.out.println("Payment failed: " + event.getId());
                break;
            default:
                // Ignore unhandled event types
                break;
        }

        return ResponseEntity.ok("received");
    }
}
