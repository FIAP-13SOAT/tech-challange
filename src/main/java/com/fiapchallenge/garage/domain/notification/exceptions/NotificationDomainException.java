package com.fiapchallenge.garage.domain.notification.exceptions;

public abstract class NotificationDomainException extends RuntimeException {
    protected NotificationDomainException(String message) {
        super(message);
    }
}
