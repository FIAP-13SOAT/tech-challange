package com.fiapchallenge.garage.application.stock.delete;

import com.fiapchallenge.garage.application.stock.exceptions.StockNotFoundException;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteStockService implements DeleteStockUseCase {

    private final StockGateway stockGateway;

    public DeleteStockService(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public void handle(UUID id) {
        Stock stock = stockGateway.findById(id)
                .orElseThrow(() -> new StockNotFoundException(id));

        stockGateway.deleteById(stock.getId());
    }
}
