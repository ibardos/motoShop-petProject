package com.ibardos.motoShop.exception;

/**
 * Thrown when an attempt is made to place an order for a MotorcycleStock
 * that has insufficient inventory.
 */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientStockException() {
        super("Insufficient stock available for this operation.");
    }
}