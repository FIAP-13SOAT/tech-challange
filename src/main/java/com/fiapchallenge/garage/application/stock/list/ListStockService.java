package com.fiapchallenge.garage.application.stock.list;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListStockService implements ListStockUseCase {

    private final StockRepository stockRepository;

    public ListStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Page<Stock> handle(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }
}