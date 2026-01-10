package com.fiapchallenge.garage.application.serviceorder.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class ServiceOrderNotFoundException extends SoatNotFoundException {
    public ServiceOrderNotFoundException(UUID id) {
        super("Service order not found with id: " + id);
    }
}
