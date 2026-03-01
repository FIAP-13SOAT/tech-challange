package com.fiapchallenge.garage.integration.config;

import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class VehicleTestUseCaseConfig {

    @Bean
    public CreateVehicleService createVehicleService(VehicleGateway vehicleGateway, CustomerRepository customerRepository) {
        return new CreateVehicleService(vehicleGateway, customerRepository);
    }
}
