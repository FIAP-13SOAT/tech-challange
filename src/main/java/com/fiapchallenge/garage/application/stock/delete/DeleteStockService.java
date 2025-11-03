package com.fiapchallenge.garage.application.stock.delete;

import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.shared.exception.ResourceNotFoundException;
import com.fiapchallenge.garage.shared.service.BaseDeleteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteStockService extends BaseDeleteService implements DeleteStockUseCase {

    private final StockRepository stockRepository;

    public DeleteStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void handle(UUID id) {
        executeDelete(
            id,
            stockId -> stockRepository.findById(stockId).isPresent(),
            () -> stockRepository.deleteById(id),
            () -> new ResourceNotFoundException("Stock", id.toString())
        );
    }
}