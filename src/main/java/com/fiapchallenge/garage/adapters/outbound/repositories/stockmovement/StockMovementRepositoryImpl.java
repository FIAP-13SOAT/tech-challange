package com.fiapchallenge.garage.adapters.outbound.repositories.stockmovement;

import com.fiapchallenge.garage.adapters.outbound.entities.StockMovementEntity;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.domain.stockmovement.StockMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StockMovementRepositoryImpl implements StockMovementRepository {

    private final JpaStockMovementRepository jpaStockMovementRepository;

    public StockMovementRepositoryImpl(JpaStockMovementRepository jpaStockMovementRepository) {
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

    private StockMovementEntity toEntity(StockMovement stockMovement) {
        return new StockMovementEntity(
                stockMovement.getId(),
                stockMovement.getStockId(),
                StockMovementEntity.MovementType.valueOf(stockMovement.getMovementType().name()),
                stockMovement.getQuantity(),
                stockMovement.getPreviousQuantity(),
                stockMovement.getNewQuantity(),
                stockMovement.getReason(),
                stockMovement.getCreatedAt()
        );
    }

    private StockMovement toDomain(StockMovementEntity entity) {
        return new StockMovement(
                entity.getId(),
                entity.getStockId(),
                StockMovement.MovementType.valueOf(entity.getMovementType().name()),
                entity.getQuantity(),
                entity.getPreviousQuantity(),
                entity.getNewQuantity(),
                entity.getReason(),
                entity.getCreatedAt()
        );
    }
}