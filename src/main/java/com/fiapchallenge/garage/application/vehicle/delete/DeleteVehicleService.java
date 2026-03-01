package com.fiapchallenge.garage.application.vehicle.delete;

import com.fiapchallenge.garage.application.vehicle.exceptions.VehicleNotFoundException;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;


import java.util.UUID;

public class DeleteVehicleService implements DeleteVehicleUseCase {

    private final VehicleGateway vehicleGateway;

    public DeleteVehicleService(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    public void handle(UUID id) {
        if (vehicleGateway.findById(id).isEmpty()) {
            throw new VehicleNotFoundException(id);
        }

        vehicleGateway.deleteById(id);
    }
}
