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


    public Stock(UUID id, String productName, String description, Integer quantity, BigDecimal unitPrice, String category, LocalDateTime createdAt, LocalDateTime updatedAt, Integer minThreshold) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.minThreshold = minThreshold;
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