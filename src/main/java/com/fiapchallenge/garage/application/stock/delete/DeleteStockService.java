package com.fiapchallenge.garage.application.stock.delete;

import com.fiapchallenge.garage.domain.stock.StockRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteStockService implements DeleteStockUseCase {

    private final StockRepository stockRepository;

    public DeleteStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void handle(UUID id) {
        if (!stockRepository.findById(id).isPresent()) {
            throw new RuntimeException("Stock not found");
        }
        stockRepository.deleteById(id);
    }
}