package com.fiapchallenge.garage.controllers.notification;

import com.fiapchallenge.garage.adapters.inbound.controller.notification.dto.NotificationResponseDTO;
import com.fiapchallenge.garage.application.notification.list.ListNotificationUseCase;
import com.fiapchallenge.garage.application.notification.markread.MarkNotificationAsReadUseCase;
import com.fiapchallenge.garage.presenters.notification.NotificationPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class NotificationController {

    private final ListNotificationUseCase listNotificationUseCase;
    private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;
    private final NotificationPresenter notificationPresenter;

    public NotificationController(
            ListNotificationUseCase listNotificationUseCase,
            MarkNotificationAsReadUseCase markNotificationAsReadUseCase,
            NotificationPresenter notificationPresenter
    ) {
        this.listNotificationUseCase = listNotificationUseCase;
        this.markNotificationAsReadUseCase = markNotificationAsReadUseCase;
        this.notificationPresenter = notificationPresenter;
    }

    public Page<NotificationResponseDTO> list(Pageable pageable) {
        return notificationPresenter.present(listNotificationUseCase.handle(pageable));
    }

    public Page<NotificationResponseDTO> listUnread(Pageable pageable) {
        return notificationPresenter.present(listNotificationUseCase.handleUnread(pageable));
    }

    public void markAsRead(UUID id) {
        markNotificationAsReadUseCase.handle(id);
    }
}
