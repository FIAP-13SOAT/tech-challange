package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import com.fiapchallenge.garage.shared.exception.SoatValidationException;
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
                .orElseThrow(() -> new SoatNotFoundException("Veículo não encontrado."));
    }
}
