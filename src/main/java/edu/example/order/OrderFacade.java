package edu.example.order;

import java.util.UUID;

public final class OrderFacade {
    private final InventoryService inventoryService = new InventoryService();
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();
    private final ShippingService shippingService = new ShippingService();
    private final NotificationService notificationService = new NotificationService();

    public OrderReceipt placeOrder(
            String customerName,
            String paymentMethod,
            Cart cart) throws InsufficientStockException, PaymentDeclinedException {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("Payment method is required");
        }
        if (cart == null) {
            throw new IllegalArgumentException("Cart is required");
        }

        Reservation reservation = inventoryService.reserve(cart);
        String paymentReference;
        try {
            paymentReference = paymentProcessor.charge(paymentMethod, reservation.totalCents());
        } catch (PaymentDeclinedException exception) {
            inventoryService.release(reservation);
            throw exception;
        }

        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String trackingNumber = shippingService.scheduleDelivery(customerName, cart);
        notificationService.sendOrderConfirmation(customerName, orderId);

        return new OrderReceipt(
                orderId,
                paymentReference,
                trackingNumber,
                customerName,
                reservation.totalCents(),
                reservation.items());
    }
}
