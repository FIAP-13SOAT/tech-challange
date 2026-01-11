package com.fiapchallenge.garage.application.stock.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class StockItemNotFoundException extends SoatNotFoundException {
    public StockItemNotFoundException(UUID stockId) {
        super("Stock item not found with id: " + stockId);
    }
}
