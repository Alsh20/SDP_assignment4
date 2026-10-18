package edu.example.order;

import java.util.HashMap;
import java.util.Map;

public final class InventoryService {
    private final Map<String, Integer> pricesInCents = new HashMap<>();
    private final Map<String, Integer> availableStock = new HashMap<>();

    public InventoryService() {
        pricesInCents.put("BOOK", 1500);
        pricesInCents.put("PEN", 200);
        availableStock.put("BOOK", 5);
        availableStock.put("PEN", 20);
    }

    public Reservation reserve(Cart cart) throws InsufficientStockException {
        if (cart == null) {
            throw new IllegalArgumentException("Cart is required");
        }
        int totalCents = 0;

        for (Map.Entry<String, Integer> item : cart.items().entrySet()) {
            String productCode = item.getKey();
            int quantity = item.getValue();
            Integer unitPrice = pricesInCents.get(productCode);
            Integer available = availableStock.get(productCode);

            if (unitPrice == null || available == null) {
                throw new IllegalArgumentException("Unknown product code: " + productCode);
            }
            if (available < quantity) {
                throw new InsufficientStockException(
                        "Not enough stock for " + productCode + ". Available: " + available);
            }
            totalCents = Math.addExact(totalCents, Math.multiplyExact(unitPrice, quantity));
        }

        for (Map.Entry<String, Integer> item : cart.items().entrySet()) {
            availableStock.compute(item.getKey(),
                    (productCode, available) -> available - item.getValue());
        }
        return new Reservation(cart.items(), totalCents);
    }

    public void release(Reservation reservation) {
        for (Map.Entry<String, Integer> item : reservation.items().entrySet()) {
            availableStock.compute(item.getKey(),
                    (productCode, available) -> available + item.getValue());
        }
    }
}
