package edu.example.order;

import java.util.concurrent.atomic.AtomicInteger;

public final class ShippingService {
    private final AtomicInteger nextTrackingNumber = new AtomicInteger(5000);

    public String scheduleDelivery(String customerName, Cart cart) {
        String trackingNumber = "SHIP-" + nextTrackingNumber.incrementAndGet();
        System.out.printf("Shipment %s scheduled for %s (%d item types)%n",
                trackingNumber, customerName, cart.items().size());
        return trackingNumber;
    }
}
