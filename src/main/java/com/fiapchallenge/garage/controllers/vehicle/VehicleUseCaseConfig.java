package com.fiapchallenge.garage.controllers.vehicle;

import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleService;
import com.fiapchallenge.garage.application.vehicle.delete.DeleteVehicleService;
import com.fiapchallenge.garage.application.vehicle.find.FindVehicleService;
import com.fiapchallenge.garage.application.vehicle.list.ListVehicleService;
import com.fiapchallenge.garage.application.vehicle.update.UpdateVehicleService;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehicleUseCaseConfig {

    @Bean
    public CreateVehicleService createVehicleService(VehicleGateway vehicleGateway, CustomerRepository customerRepository) {
        return new CreateVehicleService(vehicleGateway, customerRepository);
    }

    @Bean
    public FindVehicleService findVehicleService(VehicleGateway vehicleGateway) {
        return new FindVehicleService(vehicleGateway);
    }

    @Bean
    public ListVehicleService listVehicleService(VehicleGateway vehicleGateway) {
        return new ListVehicleService(vehicleGateway);
    }

    @Bean
    public UpdateVehicleService updateVehicleService(VehicleGateway vehicleGateway) {
        return new UpdateVehicleService(vehicleGateway);
    }

    @Bean
    public DeleteVehicleService deleteVehicleService(VehicleGateway vehicleGateway) {
        return new DeleteVehicleService(vehicleGateway);
    }
}
