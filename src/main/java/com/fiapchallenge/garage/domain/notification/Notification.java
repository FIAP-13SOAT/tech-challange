package com.fiapchallenge.garage.domain.notification;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private UUID id;
    private String type;
    private String message;
    private UUID stockId;
    private boolean read;
    private LocalDateTime createdAt;
}
