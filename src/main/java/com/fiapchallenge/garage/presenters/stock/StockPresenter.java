package com.fiapchallenge.garage.presenters.stock;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.StockDTO;
import com.fiapchallenge.garage.domain.stock.Stock;
import org.springframework.data.domain.Page;

public class StockPresenter {

    public StockDTO present(Stock stock) {
        return new StockDTO(
                stock.getId(),
                stock.getProductName(),
                stock.getDescription(),
                stock.getQuantity(),
                stock.getUnitPrice(),
                stock.getCategory(),
                stock.getCreatedAt(),
                stock.getUpdatedAt(),
                stock.getMinThreshold(),
                stock.isLowStock()
        );
    }

    public Page<StockDTO> present(Page<Stock> stockPage) {
        return stockPage.map(this::present);
    }
}
