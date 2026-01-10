package com.fiapchallenge.garage.application.vehicle.find;

import com.fiapchallenge.garage.application.vehicle.exceptions.VehicleNotFoundException;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindVehicleService implements FindVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    public FindVehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle handle(UUID id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }
}
