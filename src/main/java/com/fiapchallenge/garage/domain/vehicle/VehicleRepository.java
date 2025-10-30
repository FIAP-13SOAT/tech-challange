package com.fiapchallenge.garage.domain.vehicle;

import java.util.Optional;
import java.util.UUID;

import java.util.List;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    UUID findCustomerIdByVehicleId(UUID vehicleId);

    List<Vehicle> findByCustomerId(UUID customerId);

    Optional<Vehicle> findById(UUID id);

    Vehicle update(Vehicle vehicle);
    
    void deleteById(UUID id);
}
