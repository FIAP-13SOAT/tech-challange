package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.adapters.outbound.repositories.CustomerRepositoryImpl;
import com.fiapchallenge.garage.adapters.outbound.repositories.VehicleRepositoryImpl;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.command.CreateVehicleCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateVehicleService implements CreateVehicleUseCase {

    private final VehicleRepositoryImpl vehicleRepository;
    private final CustomerRepositoryImpl customerRepository;

    public CreateVehicleService(VehicleRepositoryImpl vehicleRepository, CustomerRepositoryImpl customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    public Vehicle create(CreateVehicleCommand command) {
        Vehicle vehicle = new Vehicle(command);

        if (!customerRepository.exists(vehicle.getCustomerId())) {
            throw new IllegalArgumentException("Necessário informar um cliente válido.");
        }

        return vehicleRepository.save(vehicle);
    }
}
