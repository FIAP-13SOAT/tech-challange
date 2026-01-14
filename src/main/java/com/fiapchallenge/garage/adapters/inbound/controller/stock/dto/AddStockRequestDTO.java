package com.fiapchallenge.garage.adapters.inbound.controller.stock.dto;

import jakarta.validation.constraints.Positive;

public record AddStockRequestDTO(
        @Positive(message = "Quantidade deve ser positiva")
        Integer quantity
) {}
