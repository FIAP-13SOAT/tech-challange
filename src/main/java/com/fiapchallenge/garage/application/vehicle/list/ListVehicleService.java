package com.fiapchallenge.garage.application.vehicle.list;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;

import java.util.List;
import java.util.UUID;

public class ListVehicleService implements ListVehicleUseCase {

    private final VehicleGateway vehicleGateway;

    public ListVehicleService(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    public List<Vehicle> handle(UUID customerId) {
        return vehicleGateway.findByCustomerId(customerId);
    }
}