package com.fiapchallenge.garage.unit.internalnotification.utils.factory;

import com.fiapchallenge.garage.application.internalnotification.command.CreateInternalNotificationCommand;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;
import com.fiapchallenge.garage.domain.internalnotification.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class InternalNotificationTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final NotificationType TYPE = NotificationType.LOW_STOCK;
    public static final UUID USER_ID = UUID.randomUUID();
    public static final UUID RESOURCE_ID = UUID.randomUUID();
    public static final String MESSAGE = "Low stock alert";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();

    public static CreateInternalNotificationCommand createCommand() {
        return new CreateInternalNotificationCommand(
                TYPE,
                RESOURCE_ID,
                MESSAGE
        );
    }

    public static InternalNotification createInternalNotification() {
        return new InternalNotification(
                ID,
                TYPE,
                false,
                USER_ID,
                RESOURCE_ID,
                MESSAGE,
                CREATED_AT,
                null
        );
    }
}
