package com.fiapchallenge.garage.application.stock.add;

import com.fiapchallenge.garage.application.stock.exceptions.StockNotFoundException;
import com.fiapchallenge.garage.application.stockmovement.create.CreateStockMovementUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import com.fiapchallenge.garage.application.stock.command.AddStockCommand;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddStockService implements AddStockUseCase {

    private final StockGateway stockGateway;
    private final CreateStockMovementUseCase createStockMovementUseCase;

    public AddStockService(StockGateway stockGateway, CreateStockMovementUseCase createStockMovementUseCase) {
        this.stockGateway = stockGateway;
        this.createStockMovementUseCase = createStockMovementUseCase;
    }

    @Override
    public Stock handle(AddStockCommand command) {
        Stock stock = stockGateway.findById(command.stockId())
                .orElseThrow(() -> new StockNotFoundException(command.stockId()));

        Integer previousQuantity = stock.getQuantity();
        Integer newQuantity = previousQuantity + command.quantity();

        stock.setQuantity(newQuantity)
             .setUpdatedAt(LocalDateTime.now());

        Stock updatedStock = stockGateway.save(stock);

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
