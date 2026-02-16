package com.fiapchallenge.garage.controllers.stock;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.AddStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.ConsumeStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.CreateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.StockDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.UpdateStockRequestDTO;
import com.fiapchallenge.garage.application.stock.add.AddStockUseCase;
import com.fiapchallenge.garage.application.stock.command.AddStockCommand;
import com.fiapchallenge.garage.application.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.application.stock.command.CreateStockCommand;
import com.fiapchallenge.garage.application.stock.command.UpdateStockCommand;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
import com.fiapchallenge.garage.application.stock.create.CreateStockUseCase;
import com.fiapchallenge.garage.application.stock.delete.DeleteStockUseCase;
import com.fiapchallenge.garage.application.stock.list.ListStockUseCase;
import com.fiapchallenge.garage.application.stock.update.UpdateStockUseCase;
import com.fiapchallenge.garage.presenters.stock.StockPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public class StockController {

    private final StockPresenter stockPresenter;
    private final CreateStockUseCase createStockUseCase;
    private final ListStockUseCase listStockUseCase;
    private final UpdateStockUseCase updateStockUseCase;
    private final DeleteStockUseCase deleteStockUseCase;
    private final ConsumeStockUseCase consumeStockUseCase;
    private final AddStockUseCase addStockUseCase;

    public StockController(
            StockPresenter stockPresenter,
            CreateStockUseCase createStockUseCase,
            ListStockUseCase listStockUseCase,
            UpdateStockUseCase updateStockUseCase,
            DeleteStockUseCase deleteStockUseCase,
            ConsumeStockUseCase consumeStockUseCase,
            AddStockUseCase addStockUseCase
    ) {
        this.stockPresenter = stockPresenter;
        this.createStockUseCase = createStockUseCase;
        this.listStockUseCase = listStockUseCase;
        this.updateStockUseCase = updateStockUseCase;
        this.deleteStockUseCase = deleteStockUseCase;
        this.consumeStockUseCase = consumeStockUseCase;
        this.addStockUseCase = addStockUseCase;
    }

    public StockDTO create(CreateStockRequestDTO createStockDTO) {
        CreateStockCommand command = new CreateStockCommand(
                createStockDTO.productName(),
                createStockDTO.description(),
                createStockDTO.unitPrice(),
                createStockDTO.category(),
                createStockDTO.minThreshold()
        );

        return stockPresenter.present(createStockUseCase.handle(command));
    }

    public Page<StockDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return stockPresenter.present(listStockUseCase.handle(pageable));
    }

    public StockDTO update(UUID id, UpdateStockRequestDTO updateStockDTO) {
        UpdateStockCommand command = new UpdateStockCommand(
                id,
                updateStockDTO.productName(),
                updateStockDTO.description(),
                updateStockDTO.unitPrice(),
                updateStockDTO.category(),
                updateStockDTO.minThreshold()
        );

        return stockPresenter.present(updateStockUseCase.handle(command));
    }

    public void delete(UUID id) {
        deleteStockUseCase.handle(id);
    }

    public StockDTO consumeStock(UUID id, ConsumeStockRequestDTO request) {
        ConsumeStockCommand command = new ConsumeStockCommand(id, request.quantity());
        return stockPresenter.present(consumeStockUseCase.handle(command));
    }

    public StockDTO addStock(UUID id, AddStockRequestDTO request) {
        AddStockCommand command = new AddStockCommand(id, request.quantity());
        return stockPresenter.present(addStockUseCase.handle(command));
    }
}
