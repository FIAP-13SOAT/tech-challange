package com.fiapchallenge.garage.unit.stockmovement.factory;

import com.fiapchallenge.garage.domain.stockmovement.StockMovement;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockMovementTestFactory {

    public static StockMovement createInMovement() {
        return new StockMovement(
                UUID.randomUUID(),
                UUID.randomUUID(),
                StockMovement.MovementType.IN,
                20,
                50,
                70,
                "Entrada de estoque",
                LocalDateTime.now()
        );
    }

    public static StockMovement createOutMovement() {
        return new StockMovement(
                UUID.randomUUID(),
                UUID.randomUUID(),
                StockMovement.MovementType.OUT,
                10,
                50,
                40,
                "Saída de estoque",
                LocalDateTime.now()
        );
    }
}