package com.fiapchallenge.garage.application.notification.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class NotificationNotFoundException extends SoatNotFoundException {
    public NotificationNotFoundException(UUID notificationId) {
        super("Notification not found with id: " + notificationId);
    }
}
