package com.fiapchallenge.garage.adapters.outbound.repositories.notification;

import com.fiapchallenge.garage.adapters.outbound.entities.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    Page<NotificationEntity> findByReadFalse(Pageable pageable);
}