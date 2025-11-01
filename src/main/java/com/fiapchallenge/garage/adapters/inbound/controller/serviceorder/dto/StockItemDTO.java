package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record StockItemDTO(
        @NotNull(message = "Stock ID é obrigatório")
        UUID stockId,
        
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser positiva")
        Integer quantity
) {
}