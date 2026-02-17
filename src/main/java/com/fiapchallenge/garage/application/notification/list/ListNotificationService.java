package com.fiapchallenge.garage.application.notification.list;

import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListNotificationService implements ListNotificationUseCase {

    private final NotificationGateway notificationGateway;

    public ListNotificationService(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    @Override
    public Page<Notification> handle(Pageable pageable) {
        return notificationGateway.findAll(pageable);
    }

    @Override
    public Page<Notification> handleUnread(Pageable pageable) {
        return notificationGateway.findByReadFalse(pageable);
    }
}