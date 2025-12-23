package com.fiapchallenge.garage.adapters.inbound.controller.stock.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.StockDTO;
import com.fiapchallenge.garage.domain.stock.Stock;
import org.springframework.data.domain.Page;

public class StockMapper {

    public static StockDTO toResponseDTO(Stock stock) {
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

    public static Page<StockDTO> toResponseDTOPage(Page<Stock> stockPage) {
        return stockPage.map(StockMapper::toResponseDTO);
    }
}