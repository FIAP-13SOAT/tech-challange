package com.fiapchallenge.garage.application.serviceorder.create;

import java.util.List;
import java.util.UUID;

public record CreateServiceOrderCommand(
        String observations,
        UUID vehicleId,
        UUID customerId,
        List<UUID> serviceTypeIdList,
        List<StockItemCommand> stockItems
) {}
