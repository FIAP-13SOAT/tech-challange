package com.fiapchallenge.garage.application.vehicle.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class VehicleNotFoundException extends SoatNotFoundException {
    public VehicleNotFoundException(UUID vehicleId) {
        super("Vehicle not found with id: " + vehicleId);
    }
}
