package com.fiapchallenge.garage.adapters.outbound.repositories.stockmovement;

import com.fiapchallenge.garage.adapters.outbound.entities.StockMovementEntity;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.domain.stockmovement.StockMovementGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StockMovementGatewayImpl implements StockMovementGateway {

    private final JpaStockMovementRepository jpaStockMovementRepository;

    public StockMovementGatewayImpl(JpaStockMovementRepository jpaStockMovementRepository) {
        this.jpaStockMovementRepository = jpaStockMovementRepository;
    }

    @Override
    public StockMovement save(StockMovement stockMovement) {
        StockMovementEntity entity = toEntity(stockMovement);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        StockMovementEntity savedEntity = jpaStockMovementRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Page<StockMovement> findByStockId(UUID stockId, Pageable pageable) {
        return jpaStockMovementRepository.findByStockId(stockId, pageable).map(this::toDomain);
    }

    @Override
    public Page<StockMovement> findAll(Pageable pageable) {
        return jpaStockMovementRepository.findAll(pageable).map(this::toDomain);
    }

    private StockMovement toDomain(StockMovementEntity entity) {
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

    private StockMovementEntity toEntity(StockMovement stockMovement) {
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
