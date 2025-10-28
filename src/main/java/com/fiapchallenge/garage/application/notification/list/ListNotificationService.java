package com.fiapchallenge.garage.application.notification.list;

import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListNotificationService implements ListNotificationUseCase {

    private final NotificationRepository notificationRepository;

    public ListNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Page<Notification> handle(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public Page<Notification> handleUnread(Pageable pageable) {
        return notificationRepository.findByReadFalse(pageable);
    }
}