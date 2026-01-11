package com.fiapchallenge.garage.domain.quote.exceptions;

public abstract class QuoteDomainException extends RuntimeException {
    protected QuoteDomainException(String message) {
        super(message);
    }
}
