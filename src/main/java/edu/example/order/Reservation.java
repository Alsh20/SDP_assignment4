package edu.example.order;

import java.util.Map;

public record Reservation(Map<String, Integer> items, int totalCents) {
    public Reservation {
        items = Map.copyOf(items);
    }
}
