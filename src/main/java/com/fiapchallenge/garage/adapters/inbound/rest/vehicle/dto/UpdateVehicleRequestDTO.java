package com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateVehicleRequestDTO(
        @NotBlank String model,
        @NotBlank String brand,
        @NotBlank String color,
        @NotNull Integer year,
        String observations
) {
}