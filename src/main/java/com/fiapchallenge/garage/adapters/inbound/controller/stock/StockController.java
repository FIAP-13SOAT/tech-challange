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
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
import com.fiapchallenge.garage.domain.stock.command.AddStockCommand;
import com.fiapchallenge.garage.application.stock.add.AddStockUseCase;

import java.util.UUID;

@RestController
@RequestMapping("stock")
@Validated
public class StockController implements StockControllerOpenApiSpec {

    private final CreateStockUseCase createStockUseCase;
    private final ListStockUseCase listStockUseCase;
    private final UpdateStockUseCase updateStockUseCase;
    private final DeleteStockUseCase deleteStockUseCase;
    private final ConsumeStockUseCase consumeStockUseCase;
    private final AddStockUseCase addStockUseCase;

    public StockController(CreateStockUseCase createStockUseCase, ListStockUseCase listStockUseCase,
                          UpdateStockUseCase updateStockUseCase, DeleteStockUseCase deleteStockUseCase,
                          ConsumeStockUseCase consumeStockUseCase, AddStockUseCase addStockUseCase) {
        this.createStockUseCase = createStockUseCase;
        this.listStockUseCase = listStockUseCase;
        this.updateStockUseCase = updateStockUseCase;
        this.deleteStockUseCase = deleteStockUseCase;
        this.consumeStockUseCase = consumeStockUseCase;
        this.addStockUseCase = addStockUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<Stock> create(@Valid @RequestBody CreateStockRequestDTO createStockDTO) {
        CreateStockCommand command = new CreateStockCommand(
                createStockDTO.productName(),
                createStockDTO.description(),
                createStockDTO.unitPrice(),
                createStockDTO.category(),
                createStockDTO.minThreshold()
        );
        return ResponseEntity.ok(createStockUseCase.handle(command));
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<Stock>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(
            page, size,
            Sort.by("createdAt").descending()
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
                updateStockDTO.unitPrice(),
                updateStockDTO.category(),
                updateStockDTO.minThreshold()
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
    @Override
    public ResponseEntity<Stock> consumeStock(@PathVariable UUID id,
                                            @RequestParam @Positive(message = "Quantidade deve ser positiva") Integer quantity) {
        ConsumeStockCommand command = new ConsumeStockCommand(id, quantity);
        return ResponseEntity.ok(consumeStockUseCase.handle(command));
    }

    @PostMapping("/{id}/add")
    @Override
    public ResponseEntity<Stock> addStock(@PathVariable UUID id,
                                         @RequestParam @Positive(message = "Quantidade deve ser positiva") Integer quantity) {
        AddStockCommand command = new AddStockCommand(id, quantity);
        return ResponseEntity.ok(addStockUseCase.handle(command));
    }

}
