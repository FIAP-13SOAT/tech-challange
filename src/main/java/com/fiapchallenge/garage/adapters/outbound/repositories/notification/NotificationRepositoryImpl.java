package com.fiapchallenge.garage.adapters.outbound.repositories.notification;

import com.fiapchallenge.garage.adapters.outbound.entities.NotificationEntity;
import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationRepositoryImpl implements NotificationRepository {

    private final JpaNotificationRepository jpaNotificationRepository;

    public NotificationRepositoryImpl(JpaNotificationRepository jpaNotificationRepository) {
        this.jpaNotificationRepository = jpaNotificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = toEntity(notification);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        
        NotificationEntity savedEntity = jpaNotificationRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return jpaNotificationRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return jpaNotificationRepository.findAll(pageable).map(this::toDomain);
    }

    @Override
    public Page<Notification> findByReadFalse(Pageable pageable) {
        return jpaNotificationRepository.findByReadFalse(pageable).map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaNotificationRepository.deleteById(id);
    }

    private NotificationEntity toEntity(Notification notification) {
        return new NotificationEntity(
                notification.getId(),
                notification.getType(),
                notification.getMessage(),
                notification.getStockId(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }

    private Notification toDomain(NotificationEntity entity) {
        return new Notification(
                entity.getId(),
                entity.getType(),
                entity.getMessage(),
                entity.getStockId(),
                entity.isRead(),
                entity.getCreatedAt()
        );
    }
}