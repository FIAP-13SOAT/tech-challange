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

    public StockMovement() {
        // Default constructor for builder pattern
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private UUID stockId;
        private MovementType movementType;
        private Integer quantity;
        private Integer previousQuantity;
        private Integer newQuantity;
        private String reason;
        private LocalDateTime createdAt;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder stockId(UUID stockId) { this.stockId = stockId; return this; }
        public Builder movementType(MovementType movementType) { this.movementType = movementType; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder previousQuantity(Integer previousQuantity) { this.previousQuantity = previousQuantity; return this; }
        public Builder newQuantity(Integer newQuantity) { this.newQuantity = newQuantity; return this; }
        public Builder reason(String reason) { this.reason = reason; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public StockMovement build() {
            StockMovement movement = new StockMovement();
            movement.id = this.id;
            movement.stockId = this.stockId;
            movement.movementType = this.movementType;
            movement.quantity = this.quantity;
            movement.previousQuantity = this.previousQuantity;
            movement.newQuantity = this.newQuantity;
            movement.reason = this.reason;
            movement.createdAt = this.createdAt;
            return movement;
        }
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