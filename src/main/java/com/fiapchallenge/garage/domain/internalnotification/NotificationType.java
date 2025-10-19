package com.fiapchallenge.garage.domain.internalnotification;

public enum NotificationType {

    LOW_STOCK("Low Stock"),
    OUT_OF_STOCK("Out of Stock");

    public final String label;

    NotificationType(String label) {
        this.label = label;
    }
}
