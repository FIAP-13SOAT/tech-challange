package com.fiapchallenge.garage.adapters.outbound.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_movement")
public class StockMovementEntity {

    @Id
    private UUID id;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "previous_quantity", nullable = false)
    private Integer previousQuantity;

    @Column(name = "new_quantity", nullable = false)
    private Integer newQuantity;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public StockMovementEntity() {
        // Default constructor required by JPA
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

        public StockMovementEntity build() {
            StockMovementEntity entity = new StockMovementEntity();
            entity.id = this.id;
            entity.stockId = this.stockId;
            entity.movementType = this.movementType;
            entity.quantity = this.quantity;
            entity.previousQuantity = this.previousQuantity;
            entity.newQuantity = this.newQuantity;
            entity.reason = this.reason;
            entity.createdAt = this.createdAt;
            return entity;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStockId() {
        return stockId;
    }

    public void setStockId(UUID stockId) {
        this.stockId = stockId;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPreviousQuantity() {
        return previousQuantity;
    }

    public void setPreviousQuantity(Integer previousQuantity) {
        this.previousQuantity = previousQuantity;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(Integer newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum MovementType {
        IN, OUT
    }
}