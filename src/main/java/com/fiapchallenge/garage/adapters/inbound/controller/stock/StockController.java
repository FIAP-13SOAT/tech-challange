package com.fiapchallenge.garage.adapters.inbound.controller.stock;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.CreateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.StockDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.UpdateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.mapper.StockMapper;
import com.fiapchallenge.garage.application.stock.create.CreateStockUseCase;
import com.fiapchallenge.garage.application.stock.delete.DeleteStockUseCase;
import com.fiapchallenge.garage.application.stock.list.ListStockUseCase;
import com.fiapchallenge.garage.application.stock.update.UpdateStockUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.application.stock.command.CreateStockCommand;
import com.fiapchallenge.garage.application.stock.command.UpdateStockCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.fiapchallenge.garage.application.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
import com.fiapchallenge.garage.application.stock.command.AddStockCommand;
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

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> create(@Valid @RequestBody CreateStockRequestDTO createStockDTO) {
        CreateStockCommand command = new CreateStockCommand(
                createStockDTO.productName(),
                createStockDTO.description(),
                createStockDTO.unitPrice(),
                createStockDTO.category(),
                createStockDTO.minThreshold()
        );
        Stock stock = createStockUseCase.handle(command);
        return ResponseEntity.ok(StockMapper.toResponseDTO(stock));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC', 'STOCK_KEEPER')")
    public ResponseEntity<Page<StockDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(
            page, size,
            Sort.by("createdAt").descending()
        );
        Page<Stock> stockPage = listStockUseCase.handle(pageable);
        return ResponseEntity.ok(StockMapper.toResponseDTOPage(stockPage));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateStockRequestDTO updateStockDTO) {
        UpdateStockCommand command = new UpdateStockCommand(
                id,
                updateStockDTO.productName(),
                updateStockDTO.description(),
                updateStockDTO.unitPrice(),
                updateStockDTO.category(),
                updateStockDTO.minThreshold()
        );
        Stock stock = updateStockUseCase.handle(command);
        return ResponseEntity.ok(StockMapper.toResponseDTO(stock));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteStockUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{id}/consume")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> consumeStock(@PathVariable UUID id,
                                            @RequestParam @Positive(message = "Quantidade deve ser positiva") Integer quantity) {
        ConsumeStockCommand command = new ConsumeStockCommand(id, quantity);
        Stock stock = consumeStockUseCase.handle(command);
        return ResponseEntity.ok(StockMapper.toResponseDTO(stock));
    }

    @Override
    @PostMapping("/{id}/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<StockDTO> addStock(@PathVariable UUID id,
                                         @RequestParam @Positive(message = "Quantidade deve ser positiva") Integer quantity) {
        AddStockCommand command = new AddStockCommand(id, quantity);
        Stock stock = addStockUseCase.handle(command);
        return ResponseEntity.ok(StockMapper.toResponseDTO(stock));
    }

}
