package com.fiapchallenge.garage.adapters.inbound.controller.stock;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.CreateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.UpdateStockRequestDTO;
import com.fiapchallenge.garage.application.stock.create.CreateStockUseCase;
import com.fiapchallenge.garage.application.stock.delete.DeleteStockUseCase;
import com.fiapchallenge.garage.application.stock.list.ListStockUseCase;
import com.fiapchallenge.garage.application.stock.update.UpdateStockUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.command.CreateStockCommand;
import com.fiapchallenge.garage.domain.stock.command.UpdateStockCommand;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;

import java.util.UUID;

@RestController
@RequestMapping("stock")
public class StockController implements StockControllerOpenApiSpec {

    private final CreateStockUseCase createStockUseCase;
    private final ListStockUseCase listStockUseCase;
    private final UpdateStockUseCase updateStockUseCase;
    private final DeleteStockUseCase deleteStockUseCase;
    private final ConsumeStockUseCase consumeStockUseCase;

    public StockController(CreateStockUseCase createStockUseCase, ListStockUseCase listStockUseCase, 
                          UpdateStockUseCase updateStockUseCase, DeleteStockUseCase deleteStockUseCase, ConsumeStockUseCase consumeStockUseCase) {
        this.createStockUseCase = createStockUseCase;
        this.listStockUseCase = listStockUseCase;
        this.updateStockUseCase = updateStockUseCase;
        this.deleteStockUseCase = deleteStockUseCase;
        this.consumeStockUseCase = consumeStockUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<Stock> create(@Valid @RequestBody CreateStockRequestDTO createStockDTO) {
        CreateStockCommand command = new CreateStockCommand(
                createStockDTO.productName(),
                createStockDTO.description(),
                createStockDTO.quantity(),
                createStockDTO.unitPrice(),
                createStockDTO.category()
        );
        return ResponseEntity.ok(createStockUseCase.handle(command));
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<Stock>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
            page, size, 
            org.springframework.data.domain.Sort.by("createdAt").descending()
        );
        return ResponseEntity.ok(listStockUseCase.handle(pageable));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Stock> update(@PathVariable UUID id, @Valid @RequestBody UpdateStockRequestDTO updateStockDTO) {
        UpdateStockCommand command = new UpdateStockCommand(
                id,
                updateStockDTO.productName(),
                updateStockDTO.description(),
                updateStockDTO.quantity(),
                updateStockDTO.unitPrice(),
                updateStockDTO.category()
        );
        return ResponseEntity.ok(updateStockUseCase.handle(command));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteStockUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/consume")
    public ResponseEntity<Stock> consumeStock(@PathVariable UUID id, @RequestParam Integer quantity) {
        ConsumeStockCommand command = new ConsumeStockCommand(id, quantity);
        return ResponseEntity.ok(consumeStockUseCase.handle(command));
    }

}