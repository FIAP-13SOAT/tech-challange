package com.fiapchallenge.garage.application.internalnotification;

import com.fiapchallenge.garage.application.internalnotification.command.CreateInternalNotificationCommand;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;

public interface CreateInternalNotificationUseCase {

    InternalNotification handle(CreateInternalNotificationCommand command);
}