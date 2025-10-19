package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

import java.util.List;
import java.util.UUID;

public record CreateServiceOrderDTO(
        String observations,
        UUID vehicleId,
        List<UUID> serviceTypeIdList
) {
}
