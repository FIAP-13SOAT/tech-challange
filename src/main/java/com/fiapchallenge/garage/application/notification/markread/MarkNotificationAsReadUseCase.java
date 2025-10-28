package com.fiapchallenge.garage.application.notification.markread;

import java.util.UUID;

public interface MarkNotificationAsReadUseCase {
    void handle(UUID id);
}