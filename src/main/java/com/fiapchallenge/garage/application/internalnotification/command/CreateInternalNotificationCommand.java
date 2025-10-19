package com.fiapchallenge.garage.application.internalnotification.command;

import com.fiapchallenge.garage.domain.internalnotification.NotificationType;

import java.util.UUID;

public record CreateInternalNotificationCommand(
        NotificationType type,
        UUID resourceId,
        String message
) {
}
