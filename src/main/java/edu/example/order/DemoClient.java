package edu.example.order;

import java.util.Map;

public final class DemoClient {
    private DemoClient() {
    }

    public static void main(String[] args) {
        OrderFacade orderFacade = new OrderFacade();
        Cart cart = new Cart(Map.of("BOOK", 1, "PEN", 2));

        try {
            orderFacade.placeOrder("Aida", "DECLINED", cart);
        } catch (PaymentDeclinedException exception) {
            System.out.println(exception.getMessage()
                    + " The facade released the stock reservation");
        } catch (InsufficientStockException exception) {
            System.err.println("Could not reserve stock: " + exception.getMessage());
            return;
        }

        try {
            OrderReceipt receipt = orderFacade.placeOrder("Aida", "CARD-1234", cart);
            System.out.printf(
                    "Order %s accepted. Payment %s, tracking %s, total $%.2f%n",
                    receipt.orderId(),
                    receipt.paymentReference(),
                    receipt.trackingNumber(),
                    receipt.totalCents() / 100.0);
        } catch (InsufficientStockException | PaymentDeclinedException exception) {
            System.err.println("Order failed: " + exception.getMessage());
        }
    }
}
