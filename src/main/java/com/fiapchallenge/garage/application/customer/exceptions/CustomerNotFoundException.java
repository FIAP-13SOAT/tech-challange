package com.fiapchallenge.garage.application.customer.exceptions;

import com.fiapchallenge.garage.domain.serviceorder.exceptions.ServiceOrderDomainException;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class CustomerNotFoundException extends SoatNotFoundException {
    public CustomerNotFoundException(UUID customerId) {
        super("Customer not found with id: " + customerId);
    }
}