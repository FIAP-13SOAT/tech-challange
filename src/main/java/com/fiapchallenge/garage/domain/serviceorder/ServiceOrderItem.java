package com.fiapchallenge.garage.domain.serviceorder;

import java.util.UUID;

public class ServiceOrderItem {
    
    private UUID id;
    private UUID stockId;
    private Integer quantity;
    
    public ServiceOrderItem(UUID stockId, Integer quantity) {
        this.stockId = stockId;
        this.quantity = quantity;
    }
    
    public ServiceOrderItem(UUID id, UUID stockId, Integer quantity) {
        this.id = id;
        this.stockId = stockId;
        this.quantity = quantity;
    }
    
    public UUID getId() {
        return id;
    }
    
    public UUID getStockId() {
        return stockId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
}