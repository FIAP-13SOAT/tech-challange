package com.fiapchallenge.garage.application.stock.list;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListStockService implements ListStockUseCase {

    private final StockGateway stockGateway;

    public ListStockService(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public Page<Stock> handle(Pageable pageable) {
        return stockGateway.findAll(pageable);
    }
}