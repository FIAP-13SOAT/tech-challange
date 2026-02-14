package com.fiapchallenge.garage.application.vehicle.find;

import com.fiapchallenge.garage.application.vehicle.exceptions.VehicleNotFoundException;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;

import java.util.UUID;

public class FindVehicleService implements FindVehicleUseCase {

    private final VehicleGateway vehicleGateway;

    public FindVehicleService(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    public Vehicle handle(UUID id) {
        return vehicleGateway.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }
}
