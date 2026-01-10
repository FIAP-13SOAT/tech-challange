package com.fiapchallenge.garage.domain.stock.exceptions;

public class InsufficientStockException extends StockDomainException {
    public InsufficientStockException(String productName, Integer available, Integer requested) {
        super(String.format("Insufficient stock for %s. Available: %d, Requested: %d", 
            productName, available, requested));
    }
}
