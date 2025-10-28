package com.fiapchallenge.garage.domain.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(UUID id);
    Page<Notification> findAll(Pageable pageable);
    Page<Notification> findByReadFalse(Pageable pageable);
    void deleteById(UUID id);
}