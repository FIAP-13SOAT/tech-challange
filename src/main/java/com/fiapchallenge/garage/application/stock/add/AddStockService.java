package com.fiapchallenge.garage.application.stock.add;

import com.fiapchallenge.garage.application.stockmovement.create.CreateStockMovementUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.application.stock.command.AddStockCommand;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddStockService implements AddStockUseCase {

    private final StockRepository stockRepository;
    private final CreateStockMovementUseCase createStockMovementUseCase;

    public AddStockService(StockRepository stockRepository, CreateStockMovementUseCase createStockMovementUseCase) {
        this.stockRepository = stockRepository;
        this.createStockMovementUseCase = createStockMovementUseCase;
    }

    @Override
    public Stock handle(AddStockCommand command) {
        Stock stock = stockRepository.findById(command.stockId())
                .orElseThrow(() -> new ResourceNotFoundException("Stock", command.stockId().toString()));

        Integer previousQuantity = stock.getQuantity();
        Integer newQuantity = previousQuantity + command.quantity();

        stock.setQuantity(newQuantity)
             .setUpdatedAt(LocalDateTime.now());

        Stock updatedStock = stockRepository.save(stock);

        createStockMovementUseCase.logMovement(
            stock.getId(),
            StockMovement.MovementType.IN,
            command.quantity(),
            previousQuantity,
            newQuantity,
            "Entrada de estoque"
        );

        return updatedStock;
    }
}
