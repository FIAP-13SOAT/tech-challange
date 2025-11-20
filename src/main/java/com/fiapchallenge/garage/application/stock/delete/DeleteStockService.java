package com.fiapchallenge.garage.application.stock.delete;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
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
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new SoatNotFoundException("Estoque não encontrado"));

        stockRepository.deleteById(stock.getId());
    }
}
