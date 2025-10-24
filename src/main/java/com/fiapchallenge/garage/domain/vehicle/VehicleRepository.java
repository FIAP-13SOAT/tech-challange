package com.fiapchallenge.garage.domain.vehicle;

import java.util.UUID;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    UUID findCustomerIdByVehicleId(UUID vehicleId);
}