package com.fiapchallenge.garage.adapters.outbound.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
public class NotificationEntity {

    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "stock_id")
    private UUID stockId;

    @Column(name = "is_read", nullable = false)
    private boolean read;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public NotificationEntity() {}

    public NotificationEntity(UUID id, String type, String message, UUID stockId, boolean read, LocalDateTime createdAt) {
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

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getStockId() {
        return stockId;
    }

    public void setStockId(UUID stockId) {
        this.stockId = stockId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}