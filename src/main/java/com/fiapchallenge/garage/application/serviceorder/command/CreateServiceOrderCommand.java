package com.fiapchallenge.garage.application.serviceorder.command;

import java.util.List;
import java.util.UUID;

public record CreateServiceOrderCommand(
        String observations,
        UUID vehicleId,
        List<UUID> serviceTypeIdList,
        List<StockItemCommand> stockItems
) {
}
