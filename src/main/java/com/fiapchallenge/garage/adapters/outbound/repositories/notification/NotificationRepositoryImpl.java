package com.fiapchallenge.garage.adapters.outbound.repositories.notification;

import com.fiapchallenge.garage.adapters.outbound.entities.NotificationEntity;
import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationRepository;
import com.fiapchallenge.garage.shared.mapper.NotificationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationRepositoryImpl implements NotificationRepository {

    private final JpaNotificationRepository jpaNotificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationRepositoryImpl(JpaNotificationRepository jpaNotificationRepository, NotificationMapper notificationMapper) {
        this.jpaNotificationRepository = jpaNotificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = notificationMapper.toEntity(notification);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        
        NotificationEntity savedEntity = jpaNotificationRepository.save(entity);
        return notificationMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return jpaNotificationRepository.findById(id).map(notificationMapper::toDomain);
    }

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return jpaNotificationRepository.findAll(pageable).map(notificationMapper::toDomain);
    }

    @Override
    public Page<Notification> findByReadFalse(Pageable pageable) {
        return jpaNotificationRepository.findByReadFalse(pageable).map(notificationMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaNotificationRepository.deleteById(id);
    }

}