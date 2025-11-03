package com.fiapchallenge.garage.adapters.outbound.repositories.stockmovement;

import com.fiapchallenge.garage.adapters.outbound.entities.StockMovementEntity;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.domain.stockmovement.StockMovementRepository;
import com.fiapchallenge.garage.shared.mapper.StockMovementMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StockMovementRepositoryImpl implements StockMovementRepository {

    private final JpaStockMovementRepository jpaStockMovementRepository;
    private final StockMovementMapper stockMovementMapper;

    public StockMovementRepositoryImpl(JpaStockMovementRepository jpaStockMovementRepository, StockMovementMapper stockMovementMapper) {
        this.jpaStockMovementRepository = jpaStockMovementRepository;
        this.stockMovementMapper = stockMovementMapper;
    }

    @Override
    public StockMovement save(StockMovement stockMovement) {
        StockMovementEntity entity = stockMovementMapper.toEntity(stockMovement);
        
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        
        StockMovementEntity savedEntity = jpaStockMovementRepository.save(entity);
        return stockMovementMapper.toDomain(savedEntity);
    }

    @Override
    public Page<StockMovement> findByStockId(UUID stockId, Pageable pageable) {
        return jpaStockMovementRepository.findByStockId(stockId, pageable).map(stockMovementMapper::toDomain);
    }

    @Override
    public Page<StockMovement> findAll(Pageable pageable) {
        return jpaStockMovementRepository.findAll(pageable).map(stockMovementMapper::toDomain);
    }

}