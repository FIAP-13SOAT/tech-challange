package com.fiapchallenge.garage.application.stock.consume;

import com.fiapchallenge.garage.application.stock.StockLevelChecker;
import com.fiapchallenge.garage.application.stockmovement.create.CreateStockMovementUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.shared.exception.InsufficientStockException;
import com.fiapchallenge.garage.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsumeStockService implements ConsumeStockUseCase {

    private final StockRepository stockRepository;
    private final StockLevelChecker stockLevelChecker;
    private final CreateStockMovementUseCase createStockMovementUseCase;

    public ConsumeStockService(StockRepository stockRepository, StockLevelChecker stockLevelChecker, CreateStockMovementUseCase createStockMovementUseCase) {
        this.stockRepository = stockRepository;
        this.stockLevelChecker = stockLevelChecker;
        this.createStockMovementUseCase = createStockMovementUseCase;
    }

    @Override
    public Stock handle(ConsumeStockCommand command) {
        Stock stock = stockRepository.findById(command.stockId())
                .orElseThrow(() -> new ResourceNotFoundException("Stock", command.stockId().toString()));

        if (stock.getQuantity() == null || stock.getQuantity() < command.quantity()) {
            throw new InsufficientStockException(
                String.format("Estoque insuficiente para %s. Disponível: %d, Solicitado: %d", 
                    stock.getProductName(), 
                    stock.getQuantity() != null ? stock.getQuantity() : 0, 
                    command.quantity())
            );
        }

        Integer previousQuantity = stock.getQuantity();
        Integer newQuantity = previousQuantity - command.quantity();
        
        stock.setQuantity(newQuantity)
                .setUpdatedAt(LocalDateTime.now());

        Stock updatedStock = stockRepository.save(stock);

        createStockMovementUseCase.logMovement(
            stock.getId(),
            StockMovement.MovementType.OUT,
            command.quantity(),
            previousQuantity,
            newQuantity,
            "Saída de estoque"
        );
        
        stockLevelChecker.checkStockLevelAsync(updatedStock);

        return updatedStock;
    }
}
