package com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.dto;

import com.fiapchallenge.garage.domain.stockmovement.StockMovement;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockMovementDTO(
        UUID id,
        UUID stockId,
        StockMovement.MovementType movementType,
        Integer quantity,
        Integer previousQuantity,
        Integer newQuantity,
        String reason,
        LocalDateTime createdAt
) {
}