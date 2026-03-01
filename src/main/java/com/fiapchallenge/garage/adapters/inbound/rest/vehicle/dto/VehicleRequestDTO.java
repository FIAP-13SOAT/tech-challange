package com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VehicleRequestDTO(
        @NotNull(message = "Necessário informar o modelo do veículo") String model,
        @NotNull(message = "Necessário informar a marca do veículo") String brand,
        @NotNull(message = "Necessário informar a placa do veículo") String licensePlate,
        @NotNull(message = "Necessário informar a cor do veículo") String color,
        @NotNull(message = "Necessário informar o ano do veículo") Integer year,
        String observations,
        @NotNull(message = "Necessário informar o dono do veículo") UUID customerId
) {
}
