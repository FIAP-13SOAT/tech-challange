package com.fiapchallenge.garage.adapters.inbound.rest.notification;

import com.fiapchallenge.garage.adapters.inbound.controller.notification.dto.NotificationResponseDTO;
import com.fiapchallenge.garage.application.notification.list.ListNotificationUseCase;
import com.fiapchallenge.garage.application.notification.markread.MarkNotificationAsReadUseCase;
import com.fiapchallenge.garage.controllers.notification.NotificationController;
import com.fiapchallenge.garage.presenters.notification.NotificationPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("notifications")
public class NotificationResource implements NotificationResourceOpenApiSpec {

    private final NotificationController notificationController;

    public NotificationResource(
            ListNotificationUseCase listNotificationUseCase,
            MarkNotificationAsReadUseCase markNotificationAsReadUseCase
    ) {
        this.notificationController = new NotificationController(
                listNotificationUseCase,
                markNotificationAsReadUseCase,
                new NotificationPresenter()
        );
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<Page<NotificationResponseDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(notificationController.list(pageable));
    }

    @Override
    @GetMapping("/unread")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<Page<NotificationResponseDTO>> listUnread(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(notificationController.listUnread(pageable));
    }

    @Override
    @PutMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        notificationController.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}
