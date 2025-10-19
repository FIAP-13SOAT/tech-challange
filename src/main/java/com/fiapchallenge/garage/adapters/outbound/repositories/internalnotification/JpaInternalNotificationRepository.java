package com.fiapchallenge.garage.adapters.outbound.repositories.internalnotification;

import com.fiapchallenge.garage.adapters.outbound.entities.InternalNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaInternalNotificationRepository extends JpaRepository<InternalNotificationEntity, UUID> {
}