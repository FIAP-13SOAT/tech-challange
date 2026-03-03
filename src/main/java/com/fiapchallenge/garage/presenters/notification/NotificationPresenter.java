package com.fiapchallenge.garage.presenters.notification;

import com.fiapchallenge.garage.adapters.inbound.controller.notification.dto.NotificationResponseDTO;
import com.fiapchallenge.garage.domain.notification.Notification;
import org.springframework.data.domain.Page;

public class NotificationPresenter {

    public NotificationResponseDTO present(Notification notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getType(),
                notification.getMessage(),
                notification.getStockId(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }

    public Page<NotificationResponseDTO> present(Page<Notification> notificationPage) {
        return notificationPage.map(this::present);
    }
}
