package com.fiapchallenge.garage.application.serviceorder.addstockitems;

import com.fiapchallenge.garage.domain.serviceorder.StockItem;

import java.util.List;
import java.util.UUID;

public record AddStockItemsCommand(UUID serviceOrderId, List<StockItem> stockItems) {
}
