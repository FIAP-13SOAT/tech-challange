package com.fiapchallenge.garage.domain.serviceorder;

public enum ServiceOrderStatus {

    RECEIVED(4),
    IN_DIAGNOSIS(3),
    AWAITING_APPROVAL(2),
    IN_PROGRESS(1),
    COMPLETED(0),
    DELIVERED(0),
    CANCELLED(0);

    public final Integer priority;

    ServiceOrderStatus(Integer priority) {
        this.priority = priority;
    }
}
