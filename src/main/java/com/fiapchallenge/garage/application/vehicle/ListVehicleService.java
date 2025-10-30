package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListVehicleService implements ListVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    public ListVehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> handle(UUID customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }
}