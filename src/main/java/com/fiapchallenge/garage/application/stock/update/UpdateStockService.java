package com.fiapchallenge.garage.application.stock.update;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stock.command.UpdateStockCommand;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
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
                .orElseThrow(() -> new SoatNotFoundException("Stock not found"));

        Stock updatedStock = existingStock
                .setProductName(command.productName())
                .setDescription(command.description())
                .setUnitPrice(command.unitPrice())
                .setCategory(command.category())
                .setUpdatedAt(LocalDateTime.now())
                .setMinThreshold(command.minThreshold());

        return stockRepository.save(updatedStock);
    }
}
