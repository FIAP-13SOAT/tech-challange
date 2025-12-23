package com.fiapchallenge.garage.application.serviceorder.removestockitems;

import com.fiapchallenge.garage.domain.serviceorder.StockItem;

import java.util.List;
import java.util.UUID;

public record RemoveStockItemsCommand(UUID serviceOrderId, List<StockItem> stockItems) {
}
