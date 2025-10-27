package com.fiapchallenge.garage.application.stock.consume;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsumeStockService implements ConsumeStockUseCase {

    private final StockRepository stockRepository;

    public ConsumeStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock handle(ConsumeStockCommand command) {
        Stock stock = stockRepository.findById(command.stockId())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        if (stock.getQuantity() < command.quantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        stock.setQuantity(stock.getQuantity() - command.quantity())
                .setUpdatedAt(LocalDateTime.now());

        Stock updatedStock = stockRepository.save(stock);
        checkStockLevelAsync(updatedStock);

        return updatedStock;
    }

    @Async
    public void checkStockLevelAsync(Stock stock) {
        if (stock.isLowStock()) {
            System.out.println("ALERTA: Estoque baixo para " + stock.getProductName());
        }
    }
}
