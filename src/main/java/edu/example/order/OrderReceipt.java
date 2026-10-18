package edu.example.order;

import java.util.Map;

public record OrderReceipt(
        String orderId,
        String paymentReference,
        String trackingNumber,
        String customerName,
        int totalCents,
        Map<String, Integer> items) {
    public OrderReceipt {
        items = Map.copyOf(items);
    }
}
