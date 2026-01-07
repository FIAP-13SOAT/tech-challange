package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;

import java.util.UUID;

public record ServiceOrderStatusDTO(
        UUID id,
        ServiceOrderStatus status,
        String statusMessage
) {
}