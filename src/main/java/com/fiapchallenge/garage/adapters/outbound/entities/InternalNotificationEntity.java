package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;
import com.fiapchallenge.garage.domain.internalnotification.NotificationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "internal_notification")
@Entity
public class InternalNotificationEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean acknowledged;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt;

    @Column(name = "resource_id")
    private UUID resourceId;

    private String message;

    public InternalNotificationEntity() {
    }

    public InternalNotificationEntity(InternalNotification internalNotification) {
        this.id = internalNotification.getId();
        this.type = internalNotification.getType();
        this.acknowledged = internalNotification.isAcknowledged();
        this.userId = internalNotification.getUserId();
        this.resourceId = internalNotification.getResourceId();
        this.message = internalNotification.getMessage();
        this.createdAt = internalNotification.getCreatedAt();
        this.acknowledgedAt = internalNotification.getAcknowledgedAt();
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
}
