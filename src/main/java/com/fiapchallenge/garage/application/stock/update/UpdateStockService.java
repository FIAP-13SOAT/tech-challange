package com.fiapchallenge.garage.application.stock.update;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stock.command.UpdateStockCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateStockService implements UpdateStockUseCase {

    private final StockRepository stockRepository;

    public UpdateStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock handle(UpdateStockCommand command) {
        Stock existingStock = stockRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Stock updatedStock = new Stock(
                command.id(),
                command.productName(),
                command.description(),
                existingStock.getQuantity(),
                command.unitPrice(),
                command.category(),
                existingStock.getCreatedAt(),
                LocalDateTime.now(),
                command.minThreshold()
        );

        return stockRepository.save(updatedStock);
    }
}