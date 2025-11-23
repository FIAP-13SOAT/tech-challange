package com.fiapchallenge.garage.application.stock.command;

import java.util.UUID;

public record AddStockCommand(
        UUID stockId,
        Integer quantity
) {
}
