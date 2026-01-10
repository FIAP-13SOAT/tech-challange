package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class ServiceOrderCustomerCannotBeNullException extends ServiceOrderDomainException {
    public ServiceOrderCustomerCannotBeNullException() {
        super("Customer cannot be null");
    }
}