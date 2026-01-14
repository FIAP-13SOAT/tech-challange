package com.fiapchallenge.garage.application.stock.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class StockNotFoundException extends SoatNotFoundException {
    public StockNotFoundException(UUID stockId) {
        super("Stock not found with id: " + stockId);
    }
}
