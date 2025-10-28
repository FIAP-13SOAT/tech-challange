package com.fiapchallenge.garage.application.stockmovement.list;

import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.domain.stockmovement.StockMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListStockMovementService implements ListStockMovementUseCase {

    private final StockMovementRepository stockMovementRepository;

    public ListStockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public Page<StockMovement> handleAll(Pageable pageable) {
        return stockMovementRepository.findAll(pageable);
    }

    @Override
    public Page<StockMovement> handleByStockId(UUID stockId, Pageable pageable) {
        return stockMovementRepository.findByStockId(stockId, pageable);
    }
}