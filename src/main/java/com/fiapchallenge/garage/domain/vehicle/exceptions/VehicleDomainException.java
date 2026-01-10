package com.fiapchallenge.garage.domain.vehicle.exceptions;

public abstract class VehicleDomainException extends RuntimeException {
    protected VehicleDomainException(String message) {
        super(message);
    }
}
