package com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto;

import java.util.UUID;

public record VehicleDTO(
        UUID id,
        String model,
        String brand,
        String licensePlate,
        UUID customerId,
        String color,
        Integer year,
        String observations
) {
}