package com.fiapchallenge.garage.application.stock.command;

import java.math.BigDecimal;

public record CreateStockCommand(
        String productName,
        String description,
        BigDecimal unitPrice,
        String category,
        Integer minThreshold
) {
}
