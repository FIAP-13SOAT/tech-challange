package com.fiapchallenge.garage.adapters.outbound.repositories.stockmovement;

import com.fiapchallenge.garage.adapters.outbound.entities.StockMovementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaStockMovementRepository extends JpaRepository<StockMovementEntity, UUID> {
    Page<StockMovementEntity> findByStockId(UUID stockId, Pageable pageable);
}