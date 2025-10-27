package com.fiapchallenge.garage.domain.stock.command;

import java.math.BigDecimal;

public record CreateStockCommand(
        String productName,
        String description,
        Integer quantity,
        BigDecimal unitPrice,
        String category
) {
}