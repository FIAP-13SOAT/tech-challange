package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.command.CreateVehicleCommand;

import java.util.UUID;

public class VehicleFixture {

    public static final UUID ID = UUID.randomUUID();
    public static final String BRAND = "Chevrolet";
    public static final String MODEL = "Camaro";
    public static final Integer YEAR = 2025;
    public static final String COLOR = "Amarelo";
    public static final String LICENSE_PLATE = "ABC-1234";
    public static final String OBSERVATIONS = "V8, 2 portas";

    public static Vehicle createVehicle(UUID customerId, CreateVehicleService createVehicleService) throws Exception {
        CreateVehicleCommand command = new CreateVehicleCommand(MODEL, BRAND, LICENSE_PLATE, COLOR, YEAR, OBSERVATIONS, customerId);

        return createVehicleService.handle(command);
    }
}
