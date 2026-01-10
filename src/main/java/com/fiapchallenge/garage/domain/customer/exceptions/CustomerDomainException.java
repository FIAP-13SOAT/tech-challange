package com.fiapchallenge.garage.domain.customer.exceptions;

public abstract class CustomerDomainException extends RuntimeException {
    protected CustomerDomainException(String message) {
        super(message);
    }
}