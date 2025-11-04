package com.fiapchallenge.garage.adapters.outbound.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock")
public class StockEntity {

    @Id
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "category")
    private String category;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "min_threshold")
    private Integer minThreshold;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String productName;
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private String category;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer minThreshold;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder productName(String productName) { this.productName = productName; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder minThreshold(Integer minThreshold) { this.minThreshold = minThreshold; return this; }

        public StockEntity build() {
            StockEntity entity = new StockEntity();
            entity.id = this.id;
            entity.productName = this.productName;
            entity.description = this.description;
            entity.quantity = this.quantity;
            entity.unitPrice = this.unitPrice;
            entity.category = this.category;
            entity.createdAt = this.createdAt;
            entity.updatedAt = this.updatedAt;
            entity.minThreshold = this.minThreshold;
            return entity;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getMinThreshold() { return minThreshold; }

    public void setMinThreshold(Integer minThreshold) { this.minThreshold = minThreshold; }
}
