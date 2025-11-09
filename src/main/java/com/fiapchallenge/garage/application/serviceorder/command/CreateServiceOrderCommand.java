package com.fiapchallenge.garage.application.serviceorder.command;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;

import java.util.List;
import java.util.UUID;

public record CreateServiceOrderCommand(
        String observations,
        UUID vehicleId,
        List<UUID> serviceTypeIdList,
        List<StockItemCommand> stockItems
) {
    public CreateServiceOrderCommand(CreateServiceOrderDTO dto) {
        this(
                dto.observations(),
                dto.vehicleId(),
                dto.serviceTypeIdList(),
                dto.stockItems() != null ?
                        dto.stockItems().stream()
                                .map(item -> new StockItemCommand(item.stockId(), item.quantity()))
                                .toList() : List.of()
        );
    }
}
