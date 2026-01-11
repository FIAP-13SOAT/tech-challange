package com.fiapchallenge.garage.application.notification.markread;

import com.fiapchallenge.garage.application.notification.exceptions.NotificationNotFoundException;
import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MarkNotificationAsReadService implements MarkNotificationAsReadUseCase {

    private final NotificationRepository notificationRepository;

    public MarkNotificationAsReadService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void handle(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        notification.setRead(true);
        notificationRepository.save(notification);
    }
}