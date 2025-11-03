package com.fiapchallenge.garage.unit.stockmovement.factory;

import com.fiapchallenge.garage.domain.stockmovement.StockMovement;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockMovementTestFactory {

    public static StockMovement createInMovement() {
        return StockMovement.builder()
                .id(UUID.randomUUID())
                .stockId(UUID.randomUUID())
                .movementType(StockMovement.MovementType.IN)
                .quantity(20)
                .previousQuantity(50)
                .newQuantity(70)
                .reason("Entrada de estoque")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static StockMovement createOutMovement() {
        return StockMovement.builder()
                .id(UUID.randomUUID())
                .stockId(UUID.randomUUID())
                .movementType(StockMovement.MovementType.OUT)
                .quantity(10)
                .previousQuantity(50)
                .newQuantity(40)
                .reason("Saída de estoque")
                .createdAt(LocalDateTime.now())
                .build();
    }
}