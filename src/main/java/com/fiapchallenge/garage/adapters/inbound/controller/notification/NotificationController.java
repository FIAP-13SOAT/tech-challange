package com.fiapchallenge.garage.adapters.inbound.controller.notification;

import com.fiapchallenge.garage.application.notification.list.ListNotificationUseCase;
import com.fiapchallenge.garage.application.notification.markread.MarkNotificationAsReadUseCase;
import com.fiapchallenge.garage.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("notifications")
public class NotificationController implements NotificationControllerOpenApiSpec {

    private final ListNotificationUseCase listNotificationUseCase;
    private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;

    public NotificationController(ListNotificationUseCase listNotificationUseCase, 
                                 MarkNotificationAsReadUseCase markNotificationAsReadUseCase) {
        this.listNotificationUseCase = listNotificationUseCase;
        this.markNotificationAsReadUseCase = markNotificationAsReadUseCase;
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<Notification>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(listNotificationUseCase.handle(pageable));
    }

    @GetMapping("/unread")
    @Override
    public ResponseEntity<Page<Notification>> listUnread(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(listNotificationUseCase.handleUnread(pageable));
    }

    @PutMapping("/{id}/read")
    @Override
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        markNotificationAsReadUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }
}