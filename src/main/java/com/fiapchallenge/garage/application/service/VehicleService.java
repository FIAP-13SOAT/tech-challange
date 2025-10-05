package com.fiapchallenge.garage.application.service;

import com.fiapchallenge.garage.adapters.outbound.repositories.VehicleRepositoryImpl;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepositoryImpl vehicleRepository;

    public VehicleService(VehicleRepositoryImpl vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle create(VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = new Vehicle(vehicleRequestDTO);

        return vehicleRepository.save(vehicle);
    }
}
