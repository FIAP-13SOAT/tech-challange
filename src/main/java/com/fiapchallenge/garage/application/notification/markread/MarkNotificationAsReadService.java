package com.fiapchallenge.garage.application.notification.markread;

import com.fiapchallenge.garage.application.notification.exceptions.NotificationNotFoundException;
import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MarkNotificationAsReadService implements MarkNotificationAsReadUseCase {

    private final NotificationGateway notificationGateway;

    public MarkNotificationAsReadService(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    @Override
    public void handle(UUID id) {
        Notification notification = notificationGateway.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        notification.setRead(true);
        notificationGateway.save(notification);
    }
}