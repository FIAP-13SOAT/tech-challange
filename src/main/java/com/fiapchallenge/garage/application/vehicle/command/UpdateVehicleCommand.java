package com.fiapchallenge.garage.application.vehicle.command;

import java.util.UUID;

public record UpdateVehicleCommand(
        UUID id,
        String model,
        String brand,
        String color,
        Integer year,
        String observations
) {}
