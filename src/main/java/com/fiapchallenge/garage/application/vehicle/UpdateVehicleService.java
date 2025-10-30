package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import com.fiapchallenge.garage.application.vehicle.command.UpdateVehicleCommand;
import org.springframework.stereotype.Service;

@Service
public class UpdateVehicleService implements UpdateVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    public UpdateVehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle handle(UpdateVehicleCommand command) {
        Vehicle existingVehicle = vehicleRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Vehicle updatedVehicle = new Vehicle(
                command.id(),
                command.model(),
                command.brand(),
                existingVehicle.getLicensePlate(),
                existingVehicle.getCustomerId(),
                command.color(),
                command.year(),
                command.observations()
        );

        return vehicleRepository.update(updatedVehicle);
    }
}
