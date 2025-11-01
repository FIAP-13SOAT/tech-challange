package com.fiapchallenge.garage.application.stock.create;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stock.command.CreateStockCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateStockService implements CreateStockUseCase {

    private final StockRepository stockRepository;

    public CreateStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock handle(CreateStockCommand command) {
        Stock stock = new Stock(
                null,
                command.productName(),
                command.description(),
                0,
                command.unitPrice(),
                command.category(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                command.minThreshold()
        );

        return stockRepository.save(stock);
    }
}