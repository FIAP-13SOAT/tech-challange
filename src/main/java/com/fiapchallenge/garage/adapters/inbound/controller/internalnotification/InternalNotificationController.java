package com.fiapchallenge.garage.adapters.inbound.controller.internalnotification;

import com.fiapchallenge.garage.adapters.inbound.controller.internalnotification.dto.InternalNotificationRequestDTO;
import com.fiapchallenge.garage.application.internalnotification.acknowledge.AcknowledgeInternalNotificationUseCase;
import com.fiapchallenge.garage.application.internalnotification.create.CreateInternalNotificationUseCase;
import com.fiapchallenge.garage.application.internalnotification.create.CreateInternalNotificationUseCase.CreateInternalNotificationCommand;
import com.fiapchallenge.garage.application.internalnotification.list.ListInternalNotificationUseCase;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;
import static com.fiapchallenge.garage.shared.auth.JwtUtil.extractUserIdFromRequest;
import com.fiapchallenge.garage.shared.pagination.CustomPageRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal-notifications")
public class InternalNotificationController implements InternalNotificationControllerOpenApiSpec {

    private final CreateInternalNotificationUseCase createInternalNotificationUseCase;
    private final ListInternalNotificationUseCase listInternalNotificationUseCase;
    private final AcknowledgeInternalNotificationUseCase acknowledgeInternalNotificationUseCase;

    public InternalNotificationController(CreateInternalNotificationUseCase createInternalNotificationUseCase,
                                        ListInternalNotificationUseCase listInternalNotificationUseCase,
                                        AcknowledgeInternalNotificationUseCase acknowledgeInternalNotificationUseCase) {
        this.createInternalNotificationUseCase = createInternalNotificationUseCase;
        this.listInternalNotificationUseCase = listInternalNotificationUseCase;
        this.acknowledgeInternalNotificationUseCase = acknowledgeInternalNotificationUseCase;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<InternalNotification>> list(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        Pageable pageable = CustomPageRequest.of(page, size);
        Page<InternalNotification> notifications = listInternalNotificationUseCase.handle(pageable);
        return ResponseEntity.ok(notifications);
    }

    @Override
    @PostMapping
    public ResponseEntity<InternalNotification> create(@Valid @RequestBody InternalNotificationRequestDTO requestDTO) {
        CreateInternalNotificationCommand command = new CreateInternalNotificationCommand(
                requestDTO.type(),
                requestDTO.resourceId(),
                requestDTO.message()
        );

        InternalNotification notification = createInternalNotificationUseCase.handle(command);
        return ResponseEntity.ok(notification);
    }

    @Override
    @PatchMapping("/{id}/acknowledge")
    public ResponseEntity<InternalNotification> acknowledge(@PathVariable UUID id, HttpServletRequest request) {
        UUID userId = extractUserIdFromRequest(request);
        InternalNotification notification = acknowledgeInternalNotificationUseCase.handle(id, userId);
        return ResponseEntity.ok(notification);
    }
}
