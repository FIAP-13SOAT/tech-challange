package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
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
            throw new SoatNotFoundException("Veículo não encontrado.");
        }

        vehicleRepository.deleteById(id);
    }
}
