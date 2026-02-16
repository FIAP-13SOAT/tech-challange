package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

import java.util.UUID;

public record ServiceOrderItemDTO(
        UUID id,
        UUID stockId,
        Integer quantity
) {
}