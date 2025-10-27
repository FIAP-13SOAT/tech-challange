package com.fiapchallenge.garage.application.stock.delete;

import java.util.UUID;

public interface DeleteStockUseCase {
    void handle(UUID id);
}