package com.fiapchallenge.garage.domain.stockmovement;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockMovement {

    private UUID id;
    private UUID stockId;
    private MovementType movementType;
    private Integer quantity;
    private Integer previousQuantity;
    private Integer newQuantity;
    private String reason;
    private LocalDateTime createdAt;

    public StockMovement(UUID id, UUID stockId, MovementType movementType, Integer quantity, 
                        Integer previousQuantity, Integer newQuantity, String reason, LocalDateTime createdAt) {
        this.id = id;
        this.stockId = stockId;
        this.movementType = movementType;
        this.quantity = quantity;
        this.previousQuantity = previousQuantity;
        this.newQuantity = newQuantity;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getStockId() {
        return stockId;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPreviousQuantity() {
        return previousQuantity;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public enum MovementType {
        IN, OUT
    }
}