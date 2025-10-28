package com.fiapchallenge.garage.application.stockmovement.create;

import com.fiapchallenge.garage.domain.stockmovement.StockMovement;

import java.util.UUID;

public interface CreateStockMovementUseCase {
    void logMovement(UUID stockId, StockMovement.MovementType type, Integer quantity, 
                    Integer previousQuantity, Integer newQuantity, String reason);
}