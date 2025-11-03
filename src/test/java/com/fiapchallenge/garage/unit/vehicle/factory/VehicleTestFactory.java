package com.fiapchallenge.garage.unit.vehicle.factory;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.application.vehicle.command.CreateVehicleCommand;

import java.util.UUID;

public class VehicleTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final String BRAND = "Chevrolet";
    public static final String MODEL = "Camaro";
    public static final Integer YEAR = 2025;
    public static final String COLOR = "Amarelo";
    public static final String LICENSE_PLATE = "ABC1234";
    public static final String OBSERVATIONS = "V8, 2 portas";
    public static final UUID CUSTOMER_ID = UUID.randomUUID();

    public static Vehicle build() {
        return Vehicle.builder()
                .id(ID)
                .model(MODEL)
                .brand(BRAND)
                .licensePlate(LICENSE_PLATE)
                .customerId(CUSTOMER_ID)
                .color(COLOR)
                .year(YEAR)
                .observations(OBSERVATIONS)
                .build();
    }

    public static CreateVehicleCommand buildCommand() {
        return new CreateVehicleCommand(MODEL, BRAND, LICENSE_PLATE, COLOR, YEAR, OBSERVATIONS, CUSTOMER_ID);
    }
}
