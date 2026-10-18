package edu.example.order;

public final class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}
