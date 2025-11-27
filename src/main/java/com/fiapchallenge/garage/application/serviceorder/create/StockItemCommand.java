package com.fiapchallenge.garage.application.serviceorder.create;

import java.util.UUID;

public record StockItemCommand(
        UUID stockId,
        Integer quantity
) {
}
