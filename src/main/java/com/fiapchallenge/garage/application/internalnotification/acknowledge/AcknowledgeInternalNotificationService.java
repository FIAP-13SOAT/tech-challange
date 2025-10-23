package com.fiapchallenge.garage.application.internalnotification.acknowledge;

import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AcknowledgeInternalNotificationService implements AcknowledgeInternalNotificationUseCase {

    private final InternalNotificationRepository internalNotificationRepository;

    public AcknowledgeInternalNotificationService(InternalNotificationRepository internalNotificationRepository) {
        this.internalNotificationRepository = internalNotificationRepository;
    }

    @Override
    public InternalNotification handle(UUID notificationId, UUID userId) {
        InternalNotification notification = internalNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.acknowledge(userId);
        
        return internalNotificationRepository.save(notification);
    }
}