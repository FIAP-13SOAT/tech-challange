package com.fiapchallenge.garage.adapters.inbound.controller.notification;

import com.fiapchallenge.garage.adapters.inbound.controller.notification.dto.NotificationResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.notification.mapper.NotificationMapper;
import com.fiapchallenge.garage.application.notification.list.ListNotificationUseCase;
import com.fiapchallenge.garage.application.notification.markread.MarkNotificationAsReadUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<Page<NotificationResponseDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(NotificationMapper.toResponseDTOPage(listNotificationUseCase.handle(pageable)));
    }

    @Override
    @GetMapping("/unread")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<Page<NotificationResponseDTO>> listUnread(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(NotificationMapper.toResponseDTOPage(listNotificationUseCase.handleUnread(pageable)));
    }

    @Override
    @PutMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        markNotificationAsReadUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }
}