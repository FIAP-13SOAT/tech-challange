package com.fiapchallenge.garage.application.vehicle.update;

import com.fiapchallenge.garage.application.vehicle.exceptions.VehicleNotFoundException;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;

public class UpdateVehicleService implements UpdateVehicleUseCase {

    private final VehicleGateway vehicleGateway;

    public UpdateVehicleService(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    public Vehicle handle(UpdateVehicleCommand command) {
        Vehicle existingVehicle = vehicleGateway.findById(command.id())
                .orElseThrow(() -> new VehicleNotFoundException(command.id()));

        Vehicle updatedVehicle = Vehicle.builder()
                .id(command.id())
                .model(command.model())
                .brand(command.brand())
                .licensePlate(existingVehicle.getLicensePlate())
                .customerId(existingVehicle.getCustomerId())
                .color(command.color())
                .year(command.year())
                .observations(command.observations())
                .build();

        return vehicleGateway.update(updatedVehicle);
    }
}
