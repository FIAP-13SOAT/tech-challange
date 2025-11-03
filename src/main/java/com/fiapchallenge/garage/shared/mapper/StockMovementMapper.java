package com.fiapchallenge.garage.shared.mapper;

import com.fiapchallenge.garage.adapters.outbound.entities.StockMovementEntity;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper extends BaseMapper<StockMovement, StockMovementEntity> {

    @Override
    public StockMovement toDomain(StockMovementEntity entity) {
        return StockMovement.builder()
                .id(entity.getId())
                .stockId(entity.getStockId())
                .movementType(StockMovement.MovementType.valueOf(entity.getMovementType().name()))
                .quantity(entity.getQuantity())
                .previousQuantity(entity.getPreviousQuantity())
                .newQuantity(entity.getNewQuantity())
                .reason(entity.getReason())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public StockMovementEntity toEntity(StockMovement stockMovement) {
        return StockMovementEntity.builder()
                .id(stockMovement.getId())
                .stockId(stockMovement.getStockId())
                .movementType(StockMovementEntity.MovementType.valueOf(stockMovement.getMovementType().name()))
                .quantity(stockMovement.getQuantity())
                .previousQuantity(stockMovement.getPreviousQuantity())
                .newQuantity(stockMovement.getNewQuantity())
                .reason(stockMovement.getReason())
                .createdAt(stockMovement.getCreatedAt())
                .build();
    }
}