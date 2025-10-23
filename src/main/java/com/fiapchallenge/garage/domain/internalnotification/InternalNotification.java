package com.fiapchallenge.garage.domain.internalnotification;

import java.time.LocalDateTime;
import java.util.UUID;

public class InternalNotification {

    private UUID id;
    private UUID userId;
    private String message;
    private UUID resourceId;
    private boolean acknowledged;
    private NotificationType type;
    private LocalDateTime createdAt;
    private LocalDateTime acknowledgedAt;

    public InternalNotification() {
    }

    public static InternalNotification fromType(NotificationType type, UUID resourceId, String message) {
        InternalNotification notification = new InternalNotification();

        notification.type = type;
        notification.acknowledged = false;
        notification.resourceId = resourceId;
        notification.message = message;
        notification.createdAt = LocalDateTime.now();

        return notification;
    }

    public InternalNotification(UUID id, NotificationType type, boolean acknowledged, UUID userId, UUID resourceId, String message, LocalDateTime createdAt, LocalDateTime acknowledgedAt) {
        this.id = id;
        this.type = type;
        this.acknowledged = acknowledged;
        this.userId = userId;
        this.resourceId = resourceId;
        this.message = message;
        this.createdAt = createdAt;
        this.acknowledgedAt = acknowledgedAt;
    }

    public UUID getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public String getMessage() {
        return message;
    }

    public void acknowledge(UUID userId) {
        this.acknowledged = true;
        this.userId = userId;
        this.acknowledgedAt = LocalDateTime.now();
    }
}
