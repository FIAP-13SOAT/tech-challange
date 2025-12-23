package com.fiapchallenge.garage.adapters.inbound.controller.notification.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.notification.dto.NotificationResponseDTO;
import com.fiapchallenge.garage.domain.notification.Notification;
import org.springframework.data.domain.Page;

public class NotificationMapper {

    public static NotificationResponseDTO toResponseDTO(Notification notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getType(),
                notification.getMessage(),
                notification.getStockId(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }

    public static Page<NotificationResponseDTO> toResponseDTOPage(Page<Notification> notificationPage) {
        return notificationPage.map(NotificationMapper::toResponseDTO);
    }
}