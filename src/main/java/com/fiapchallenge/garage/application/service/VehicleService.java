package com.fiapchallenge.garage.application.service;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.CustomerRepositoryImpl;
import com.fiapchallenge.garage.adapters.outbound.repositories.vehicle.VehicleRepositoryImpl;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepositoryImpl vehicleRepository;
    private final CustomerRepositoryImpl customerRepository;

    public VehicleService(VehicleRepositoryImpl vehicleRepository, CustomerRepositoryImpl customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    public Vehicle create(VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = new Vehicle(vehicleRequestDTO);

        if (!customerRepository.exists(vehicle.getCustomerId())) {
            throw new IllegalArgumentException("Necessário informar um cliente válido.");
        }

        return vehicleRepository.save(vehicle);
    }
}
