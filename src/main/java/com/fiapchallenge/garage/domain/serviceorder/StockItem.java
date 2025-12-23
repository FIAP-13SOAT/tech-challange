package com.fiapchallenge.garage.domain.serviceorder;

import java.util.UUID;

public class StockItem {
    private UUID stockId;
    private Integer quantity;

    public StockItem(UUID stockId, Integer quantity) {
        this.stockId = stockId;
        this.quantity = quantity;
    }

    public UUID getStockId() {
        return stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}