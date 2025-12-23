package com.fiapchallenge.garage.adapters.inbound.controller.stockmovement;

import com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.dto.StockMovementDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.mapper.StockMovementMapper;
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
    public ResponseEntity<Page<StockMovementDTO>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<StockMovement> stockMovementPage = listStockMovementUseCase.handleAll(pageable);
        return ResponseEntity.ok(StockMovementMapper.toResponseDTOPage(stockMovementPage));
    }

    @GetMapping("/stock/{stockId}")
    @Override
    public ResponseEntity<Page<StockMovementDTO>> listByStockId(
            @PathVariable UUID stockId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<StockMovement> stockMovementPage = listStockMovementUseCase.handleByStockId(stockId, pageable);
        return ResponseEntity.ok(StockMovementMapper.toResponseDTOPage(stockMovementPage));
    }
}