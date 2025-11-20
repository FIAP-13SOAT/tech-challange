package com.fiapchallenge.garage.application.serviceorder.removestockitems;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;

import java.util.List;
import java.util.UUID;

public record RemoveStockItemsCommand(UUID serviceOrderId, List<StockItemDTO> stockItems) {
}
