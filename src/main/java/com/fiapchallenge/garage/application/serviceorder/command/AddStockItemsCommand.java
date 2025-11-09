package com.fiapchallenge.garage.application.serviceorder.command;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;

import java.util.List;
import java.util.UUID;

public record AddStockItemsCommand(UUID serviceOrderId, List<StockItemDTO> stockItems) {
}