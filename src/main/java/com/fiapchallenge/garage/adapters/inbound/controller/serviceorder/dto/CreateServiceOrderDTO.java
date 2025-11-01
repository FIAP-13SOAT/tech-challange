package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public record CreateServiceOrderDTO(
        String observations,
        UUID vehicleId,
        List<UUID> serviceTypeIdList,
        
        @Valid
        List<StockItemDTO> stockItems
) {
}
