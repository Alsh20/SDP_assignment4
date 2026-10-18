package edu.example.order;

public final class NotificationService {
    public void sendOrderConfirmation(String customerName, String orderId) {
        System.out.printf("Confirmation sent to %s for order %s%n", customerName, orderId);
    }
}
