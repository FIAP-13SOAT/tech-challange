package com.fiapchallenge.garage.domain.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository {
    Stock save(Stock stock);
    Optional<Stock> findById(UUID id);
    Page<Stock> findAll(Pageable pageable);
    void deleteById(UUID id);
}
