package com.fiapchallenge.garage.domain.serviceorder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "ServiceOrderRequest", description = "Dados para criação de um cliente")
public record ServiceOrderRequestDTO(
        @NotNull(message = "Necessário informar a descricao da Ordem de Serviço") String description,
        @NotNull(message = "Necessário informar o identificador do veículo") UUID vehicleId
) {
}
