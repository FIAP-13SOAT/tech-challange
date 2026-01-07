package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public abstract class ServiceOrderDomainException extends RuntimeException {
    protected ServiceOrderDomainException(String message) {
        super(message);
    }
}