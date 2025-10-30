package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;

import java.util.UUID;

public interface FindVehicleUseCase {
    Vehicle handle(UUID id);
}