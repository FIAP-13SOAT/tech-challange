package com.fiapchallenge.garage.domain.stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Stock {

    private UUID id;
    private String productName;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
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

        public Stock build() {
            Stock stock = new Stock();
            stock.id = this.id;
            stock.productName = this.productName;
            stock.description = this.description;
            stock.quantity = this.quantity;
            stock.unitPrice = this.unitPrice;
            stock.category = this.category;
            stock.createdAt = this.createdAt;
            stock.updatedAt = this.updatedAt;
            stock.minThreshold = this.minThreshold;
            return stock;
        }
    }

    public UUID getId() {
        return id;
    }

    public Stock setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Stock setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Stock setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Stock setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Stock setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Stock setCategory(String category) {
        this.category = category;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Stock setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Stock setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Integer getMinThreshold() {
        return minThreshold;
    }

    public Stock setMinThreshold(Integer minThreshold) {
        this.minThreshold = minThreshold;
        return this;
    }

    public boolean isLowStock() {
        return minThreshold != null && quantity <= minThreshold;
    }
}
