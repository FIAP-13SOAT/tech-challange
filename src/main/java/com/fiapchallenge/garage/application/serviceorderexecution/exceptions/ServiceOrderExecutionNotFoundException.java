package com.fiapchallenge.garage.application.serviceorderexecution.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class ServiceOrderExecutionNotFoundException extends SoatNotFoundException {
    public ServiceOrderExecutionNotFoundException(UUID serviceOrderId) {
        super("Service order execution not found with id: " + serviceOrderId);
    }
}
