package com.fiapchallenge.garage.adapters.inbound.controller.stockmovement;

import com.fiapchallenge.garage.application.stockmovement.list.ListStockMovementUseCase;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("stock-movements")
public class StockMovementController implements StockMovementControllerOpenApiSpec {

    private final ListStockMovementUseCase listStockMovementUseCase;

    public StockMovementController(ListStockMovementUseCase listStockMovementUseCase) {
        this.listStockMovementUseCase = listStockMovementUseCase;
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<StockMovement>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(listStockMovementUseCase.handleAll(pageable));
    }

    @GetMapping("/stock/{stockId}")
    @Override
    public ResponseEntity<Page<StockMovement>> listByStockId(
            @PathVariable UUID stockId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(listStockMovementUseCase.handleByStockId(stockId, pageable));
    }
}