package com.fiapchallenge.garage.unit.notification.factory;

import com.fiapchallenge.garage.domain.notification.Notification;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationTestFactory {

    public static Notification createLowStockNotification() {
        return new Notification(
                UUID.randomUUID(),
                "LOW_STOCK",
                "Estoque baixo para produto: Óleo Motor 5W30",
                UUID.randomUUID(),
                false,
                LocalDateTime.now()
        );
    }

    public static Notification createReadNotification() {
        return new Notification(
                UUID.randomUUID(),
                "LOW_STOCK",
                "Estoque baixo para produto: Filtro de Ar",
                UUID.randomUUID(),
                true,
                LocalDateTime.now()
        );
    }
}