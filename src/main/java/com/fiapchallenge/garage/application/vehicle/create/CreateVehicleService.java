package com.fiapchallenge.garage.application.vehicle.create;

import com.fiapchallenge.garage.application.customer.exceptions.CustomerNotFoundException;
import com.fiapchallenge.garage.domain.customer.CustomerGateway;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import com.fiapchallenge.garage.domain.vehicle.exceptions.InvalidLicensePlateException;

public class CreateVehicleService implements CreateVehicleUseCase {

    private final VehicleGateway vehicleGateway;
    private final CustomerGateway customerGateway;

    public CreateVehicleService(VehicleGateway vehicleGateway, CustomerGateway customerGateway) {
        this.vehicleGateway = vehicleGateway;
        this.customerGateway = customerGateway;
    }

    @Override
    public Vehicle handle(CreateVehicleCommand command) {
        if (!this.isValidBrazilianLicensePlate(command.licensePlate())) {
            throw new InvalidLicensePlateException(command.licensePlate());
        }

        if (!customerGateway.exists(command.customerId())) {
            throw new CustomerNotFoundException(command.customerId());
        }

        Vehicle vehicle = Vehicle.builder()
            .model(command.model())
            .brand(command.brand())
            .licensePlate(command.licensePlate())
            .customerId(command.customerId())
            .color(command.color())
            .year(command.year())
            .observations(command.observations())
            .build();

        return vehicleGateway.save(vehicle);
    }

    private boolean isValidBrazilianLicensePlate(String licensePlate) {
        if (licensePlate == null) return false;
        return licensePlate.matches("^[A-Z]{3}\\d{4}$") || licensePlate.matches("^[A-Z]{3}\\d[A-Z]\\d{2}$");
    }
}
