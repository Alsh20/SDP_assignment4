package edu.example.order;

import java.util.concurrent.atomic.AtomicInteger;

public final class PaymentProcessor {
    private final AtomicInteger nextPaymentNumber = new AtomicInteger(1000);

    public String charge(String paymentMethod, int totalCents) throws PaymentDeclinedException {
        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("A payment method is required");
        }
        if ("DECLINED".equalsIgnoreCase(paymentMethod)) {
            throw new PaymentDeclinedException("The payment was declined");
        }
        if (totalCents <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        return "PAY-" + nextPaymentNumber.incrementAndGet();
    }
}
