package com.fiapchallenge.garage.adapters.inbound.rest.stock;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.AddStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.ConsumeStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.CreateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.StockDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.UpdateStockRequestDTO;
import com.fiapchallenge.garage.application.stock.add.AddStockUseCase;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
import com.fiapchallenge.garage.application.stock.create.CreateStockUseCase;
import com.fiapchallenge.garage.application.stock.delete.DeleteStockUseCase;
import com.fiapchallenge.garage.application.stock.list.ListStockUseCase;
import com.fiapchallenge.garage.application.stock.update.UpdateStockUseCase;
import com.fiapchallenge.garage.controllers.stock.StockController;
import com.fiapchallenge.garage.presenters.stock.StockPresenter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("stock")
public class StockResource implements StockResourceOpenApiSpec {

    private final StockController stockController;

    public StockResource(
            CreateStockUseCase createStockUseCase,
            ListStockUseCase listStockUseCase,
            UpdateStockUseCase updateStockUseCase,
            DeleteStockUseCase deleteStockUseCase,
            ConsumeStockUseCase consumeStockUseCase,
            AddStockUseCase addStockUseCase
    ) {
        this.stockController = new StockController(
                new StockPresenter(),
                createStockUseCase,
                listStockUseCase,
                updateStockUseCase,
                deleteStockUseCase,
                consumeStockUseCase,
                addStockUseCase
        );
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> create(@Valid @RequestBody CreateStockRequestDTO createStockDTO) {
        return ResponseEntity.ok(stockController.create(createStockDTO));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC', 'STOCK_KEEPER')")
    public ResponseEntity<Page<StockDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(stockController.list(page, size));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateStockRequestDTO updateStockDTO) {
        return ResponseEntity.ok(stockController.update(id, updateStockDTO));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        stockController.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{id}/consume")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> consumeStock(@PathVariable UUID id, @Valid @RequestBody ConsumeStockRequestDTO request) {
        return ResponseEntity.ok(stockController.consumeStock(id, request));
    }

    @Override
    @PostMapping("/{id}/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> addStock(@PathVariable UUID id, @Valid @RequestBody AddStockRequestDTO request) {
        return ResponseEntity.ok(stockController.addStock(id, request));
    }
}
