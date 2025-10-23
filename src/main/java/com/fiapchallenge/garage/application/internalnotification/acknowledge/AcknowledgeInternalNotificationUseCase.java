package com.fiapchallenge.garage.application.internalnotification.acknowledge;

import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;

import java.util.UUID;

public interface AcknowledgeInternalNotificationUseCase {

    InternalNotification handle(UUID notificationId, UUID userId);
}