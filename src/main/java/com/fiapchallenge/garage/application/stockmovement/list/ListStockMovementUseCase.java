package com.fiapchallenge.garage.application.stockmovement.list;

import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ListStockMovementUseCase {
    Page<StockMovement> handleAll(Pageable pageable);
    Page<StockMovement> handleByStockId(UUID stockId, Pageable pageable);
}