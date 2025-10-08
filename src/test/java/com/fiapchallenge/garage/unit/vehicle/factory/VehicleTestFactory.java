package com.fiapchallenge.garage.unit.vehicle.factory;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.command.CreateVehicleCommand;

import java.util.UUID;

public class VehicleTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final String BRAND = "Chevrolet";
    public static final String MODEL = "Camaro";
    public static final Integer YEAR = 2025;
    public static final String COLOR = "Amarelo";
    public static final String LICENSE_PLATE = "ABC-1234";
    public static final String OBSERVATIONS = "V8, 2 portas";
    public static final UUID CUSTOMER_ID = UUID.randomUUID();

    public static Vehicle build() {
        return new Vehicle(ID, MODEL, BRAND, LICENSE_PLATE, CUSTOMER_ID, COLOR, YEAR, OBSERVATIONS);
    }

    public static CreateVehicleCommand buildCommand() {
        return new CreateVehicleCommand(MODEL, BRAND, LICENSE_PLATE, COLOR, YEAR, OBSERVATIONS, CUSTOMER_ID);
    }
}
