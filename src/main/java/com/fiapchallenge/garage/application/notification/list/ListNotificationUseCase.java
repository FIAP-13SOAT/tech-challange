package com.fiapchallenge.garage.application.notification.list;

import com.fiapchallenge.garage.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListNotificationUseCase {
    Page<Notification> handle(Pageable pageable);
    Page<Notification> handleUnread(Pageable pageable);
}