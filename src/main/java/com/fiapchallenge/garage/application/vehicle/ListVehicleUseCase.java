package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;

import java.util.List;
import java.util.UUID;

public interface ListVehicleUseCase {
    List<Vehicle> handle(UUID customerId);
}