package com.fiapchallenge.garage.application.stockmovement.create;

import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.domain.stockmovement.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateStockMovementService implements CreateStockMovementUseCase {

    private final StockMovementRepository stockMovementRepository;

    public CreateStockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public void logMovement(UUID stockId, StockMovement.MovementType type, Integer quantity, 
                           Integer previousQuantity, Integer newQuantity, String reason) {
        StockMovement movement = StockMovement.builder()
                .stockId(stockId)
                .movementType(type)
                .quantity(quantity)
                .previousQuantity(previousQuantity)
                .newQuantity(newQuantity)
                .reason(reason)
                .createdAt(LocalDateTime.now())
                .build();
        
        stockMovementRepository.save(movement);
    }
}