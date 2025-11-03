package com.fiapchallenge.garage.application.stock;

import com.fiapchallenge.garage.domain.stock.Stock;

public interface StockLevelChecker {
    void checkStockLevelAsync(Stock stock);
}