package com.fiapchallenge.garage.domain.vehicle.command;

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
