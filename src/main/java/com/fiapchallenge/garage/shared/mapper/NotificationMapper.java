package com.fiapchallenge.garage.shared.mapper;

import com.fiapchallenge.garage.adapters.outbound.entities.NotificationEntity;
import com.fiapchallenge.garage.domain.notification.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper extends BaseMapper<Notification, NotificationEntity> {

    @Override
    public Notification toDomain(NotificationEntity entity) {
        return new Notification(
                entity.getId(),
                entity.getType(),
                entity.getMessage(),
                entity.getStockId(),
                entity.isRead(),
                entity.getCreatedAt()
        );
    }

    @Override
    public NotificationEntity toEntity(Notification notification) {
        return new NotificationEntity(
                notification.getId(),
                notification.getType(),
                notification.getMessage(),
                notification.getStockId(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}