package com.fiapchallenge.garage.application.stock.update;

import com.fiapchallenge.garage.application.stock.exceptions.StockNotFoundException;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import com.fiapchallenge.garage.application.stock.command.UpdateStockCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateStockService implements UpdateStockUseCase {

    private final StockGateway stockGateway;

    public UpdateStockService(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public Stock handle(UpdateStockCommand command) {
        Stock existingStock = stockGateway.findById(command.id())
                .orElseThrow(() -> new StockNotFoundException(command.id()));

        Stock updatedStock = existingStock
                .setProductName(command.productName())
                .setDescription(command.description())
                .setUnitPrice(command.unitPrice())
                .setCategory(command.category())
                .setUpdatedAt(LocalDateTime.now())
                .setMinThreshold(command.minThreshold());

        return stockGateway.save(updatedStock);
    }
}
