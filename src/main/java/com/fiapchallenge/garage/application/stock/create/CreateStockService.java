package com.fiapchallenge.garage.application.stock.create;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import com.fiapchallenge.garage.application.stock.command.CreateStockCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateStockService implements CreateStockUseCase {

    private final StockGateway stockGateway;

    public CreateStockService(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public Stock handle(CreateStockCommand command) {
        Stock stock = Stock.builder()
                .productName(command.productName())
                .description(command.description())
                .quantity(0)
                .unitPrice(command.unitPrice())
                .category(command.category())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .minThreshold(command.minThreshold())
                .build();

        return stockGateway.save(stock);
    }
}
