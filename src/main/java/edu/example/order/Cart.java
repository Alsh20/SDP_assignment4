package edu.example.order;

import java.util.Map;

public record Cart(Map<String, Integer> items) {
    public Cart {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("A cart must contain at least one item");
        }
        items = Map.copyOf(items);
        for (Map.Entry<String, Integer> item : items.entrySet()) {
            if (item.getKey().isBlank() || item.getValue() <= 0) {
                throw new IllegalArgumentException("Product codes and quantities must be valid");
            }
        }
    }
}
