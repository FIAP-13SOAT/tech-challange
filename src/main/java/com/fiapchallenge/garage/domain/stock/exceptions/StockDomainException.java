package com.fiapchallenge.garage.domain.stock.exceptions;

public abstract class StockDomainException extends RuntimeException {
    protected StockDomainException(String message) {
        super(message);
    }
}
