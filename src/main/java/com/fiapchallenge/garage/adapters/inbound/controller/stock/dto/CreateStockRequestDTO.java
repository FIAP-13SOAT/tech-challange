package com.fiapchallenge.garage.adapters.inbound.controller.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(name = "CreateStockRequest", description = "Dados para criação de um item de estoque")
public record CreateStockRequestDTO(
        @NotNull(message = "Nome do produto é obrigatório") String productName,
        String description,
        @NotNull(message = "Preço unitário é obrigatório") @Positive(message = "Preço deve ser positivo") BigDecimal unitPrice,
        String category,
        @Positive(message = "Threshold mínimo deve ser positivo") Integer minThreshold
) {
}