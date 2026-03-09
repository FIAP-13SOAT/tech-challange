package com.fiapchallenge.garage.controllers.stockmovement;

import com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.dto.StockMovementDTO;
import com.fiapchallenge.garage.application.stockmovement.list.ListStockMovementUseCase;
import com.fiapchallenge.garage.presenters.stockmovement.StockMovementPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public class StockMovementController {

    private final StockMovementPresenter stockMovementPresenter;
    private final ListStockMovementUseCase listStockMovementUseCase;

    public StockMovementController(StockMovementPresenter stockMovementPresenter, ListStockMovementUseCase listStockMovementUseCase) {
        this.stockMovementPresenter = stockMovementPresenter;
        this.listStockMovementUseCase = listStockMovementUseCase;
    }

    public Page<StockMovementDTO> listAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return stockMovementPresenter.present(listStockMovementUseCase.handleAll(pageable));
    }

    public Page<StockMovementDTO> listByStockId(UUID stockId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return stockMovementPresenter.present(listStockMovementUseCase.handleByStockId(stockId, pageable));
    }
}
