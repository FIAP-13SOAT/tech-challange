package com.fiapchallenge.garage.adapters.inbound.rest.stockmovement;

import com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.dto.StockMovementDTO;
import com.fiapchallenge.garage.application.stockmovement.list.ListStockMovementUseCase;
import com.fiapchallenge.garage.controllers.stockmovement.StockMovementController;
import com.fiapchallenge.garage.presenters.stockmovement.StockMovementPresenter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("stock-movements")
public class StockMovementResource implements StockMovementResourceOpenApiSpec {

    private final StockMovementController stockMovementController;

    public StockMovementResource(ListStockMovementUseCase listStockMovementUseCase) {
        this.stockMovementController = new StockMovementController(new StockMovementPresenter(), listStockMovementUseCase);
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<StockMovementDTO>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(stockMovementController.listAll(page, size));
    }

    @GetMapping("/stock/{stockId}")
    @Override
    public ResponseEntity<Page<StockMovementDTO>> listByStockId(
            @PathVariable UUID stockId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(stockMovementController.listByStockId(stockId, page, size));
    }
}
