package com.fiapchallenge.garage.domain.notification;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

    private UUID id;
    private String type;
    private String message;
    private UUID stockId;
    private boolean read;
    private LocalDateTime createdAt;

    public Notification(UUID id, String type, String message, UUID stockId, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.stockId = stockId;
        this.read = read;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public Notification setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Notification setType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Notification setMessage(String message) {
        this.message = message;
        return this;
    }

    public UUID getStockId() {
        return stockId;
    }

    public Notification setStockId(UUID stockId) {
        this.stockId = stockId;
        return this;
    }

    public boolean isRead() {
        return read;
    }

    public Notification setRead(boolean read) {
        this.read = read;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Notification setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}