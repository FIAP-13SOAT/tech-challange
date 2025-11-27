package com.fiapchallenge.garage.application.stock.command;

import java.util.UUID;

public record ConsumeStockCommand(
        UUID stockId,
        Integer quantity
) {
}
