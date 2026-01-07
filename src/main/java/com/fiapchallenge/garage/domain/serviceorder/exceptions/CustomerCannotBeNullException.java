package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class CustomerCannotBeNullException extends ServiceOrderDomainException {
    public CustomerCannotBeNullException() {
        super("Customer cannot be null");
    }
}