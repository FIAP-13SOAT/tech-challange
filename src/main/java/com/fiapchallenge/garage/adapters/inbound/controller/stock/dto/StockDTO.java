package com.fiapchallenge.garage.adapters.inbound.controller.stock.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record StockDTO(
        UUID id,
        String productName,
        String description,
        Integer quantity,
        BigDecimal unitPrice,
        String category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer minThreshold,
        boolean isLowStock
) {
}