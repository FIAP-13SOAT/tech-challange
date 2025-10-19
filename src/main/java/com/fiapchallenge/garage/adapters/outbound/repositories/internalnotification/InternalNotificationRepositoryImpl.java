package com.fiapchallenge.garage.adapters.outbound.repositories.internalnotification;

import com.fiapchallenge.garage.adapters.outbound.entities.InternalNotificationEntity;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotificationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class InternalNotificationRepositoryImpl implements InternalNotificationRepository {

    private final JpaInternalNotificationRepository jpaInternalNotificationRepository;

    public InternalNotificationRepositoryImpl(JpaInternalNotificationRepository jpaInternalNotificationRepository) {
        this.jpaInternalNotificationRepository = jpaInternalNotificationRepository;
    }

    @Override
    public InternalNotification save(InternalNotification internalNotification) {
        InternalNotificationEntity entity = new InternalNotificationEntity(internalNotification);
        entity = jpaInternalNotificationRepository.save(entity);

        return this.convertFromEntity(entity);
    }

    @Override
    public boolean exists(UUID id) {
        return jpaInternalNotificationRepository.existsById(id);
    }

    @Override
    public Optional<InternalNotification> findById(UUID id) {
        return jpaInternalNotificationRepository.findById(id)
                .map(this::convertFromEntity);
    }

    private InternalNotification convertFromEntity(InternalNotificationEntity entity) {
        return new InternalNotification(
                entity.getId(),
                entity.getType(),
                entity.isAcknowledged(),
                entity.getUserId(),
                entity.getResourceId(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getAcknowledgedAt()
        );
    }
}
