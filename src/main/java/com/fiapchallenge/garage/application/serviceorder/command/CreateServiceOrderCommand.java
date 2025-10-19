package com.fiapchallenge.garage.application.serviceorder.command;

import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.util.List;
import java.util.UUID;

public record CreateServiceOrderCommand(
        String observations,
        UUID vehicleId,
        List<UUID> serviceTypeIdList
) {
}
