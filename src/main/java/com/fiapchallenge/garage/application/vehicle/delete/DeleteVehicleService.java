package com.fiapchallenge.garage.application.vehicle.delete;

import com.fiapchallenge.garage.application.vehicle.exceptions.VehicleNotFoundException;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class DeleteVehicleService implements DeleteVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    public DeleteVehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void handle(UUID id) {
        if (vehicleRepository.findById(id).isEmpty()) {
            throw new VehicleNotFoundException(id);
        }

        vehicleRepository.deleteById(id);
    }
}
