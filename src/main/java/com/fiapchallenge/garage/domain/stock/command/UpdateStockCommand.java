package com.fiapchallenge.garage.domain.stock.command;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateStockCommand(
        UUID id,
        String productName,
        String description,
        BigDecimal unitPrice,
        String category,
        Integer minThreshold
) {
}