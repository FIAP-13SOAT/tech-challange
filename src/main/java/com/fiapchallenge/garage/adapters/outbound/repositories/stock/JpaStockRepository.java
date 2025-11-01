package com.fiapchallenge.garage.adapters.outbound.repositories.stock;

import com.fiapchallenge.garage.adapters.outbound.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaStockRepository extends JpaRepository<StockEntity, UUID> {
    Optional<StockEntity> findByProductName(String productName);
}