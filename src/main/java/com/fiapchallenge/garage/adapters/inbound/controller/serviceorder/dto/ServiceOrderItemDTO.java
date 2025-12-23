package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;

import java.util.UUID;

public record ServiceOrderItemDTO(
        UUID id,
        UUID stockId,
        Integer quantity
) {
    public static ServiceOrderItemDTO fromDomain(ServiceOrderItem item) {
        return new ServiceOrderItemDTO(
                item.getId(),
                item.getStockId(),
                item.getQuantity()
        );
    }
}