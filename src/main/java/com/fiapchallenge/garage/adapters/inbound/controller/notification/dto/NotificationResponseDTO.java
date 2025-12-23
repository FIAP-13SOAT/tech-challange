package com.fiapchallenge.garage.adapters.inbound.controller.notification.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO(
        UUID id,
        String type,
        String message,
        UUID stockId,
        boolean read,
        LocalDateTime createdAt
) {
}