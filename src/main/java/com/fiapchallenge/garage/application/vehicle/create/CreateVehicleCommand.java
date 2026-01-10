package com.fiapchallenge.garage.application.vehicle.create;

import java.util.UUID;

public record CreateVehicleCommand(
        String model,
        String brand,
        String licensePlate,
        String color,
        Integer year,
        String observations,
        UUID customerId
) {
}
