package com.fiapchallenge.garage.shared.mapper;

import com.fiapchallenge.garage.adapters.outbound.entities.StockEntity;
import com.fiapchallenge.garage.domain.stock.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper extends BaseMapper<Stock, StockEntity> {

    @Override
    public Stock toDomain(StockEntity entity) {
        return Stock.builder()
                .id(entity.getId())
                .productName(entity.getProductName())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .minThreshold(entity.getMinThreshold())
                .build();
    }

    @Override
    public StockEntity toEntity(Stock stock) {
        return StockEntity.builder()
                .id(stock.getId())
                .productName(stock.getProductName())
                .description(stock.getDescription())
                .quantity(stock.getQuantity())
                .unitPrice(stock.getUnitPrice())
                .category(stock.getCategory())
                .createdAt(stock.getCreatedAt())
                .updatedAt(stock.getUpdatedAt())
                .minThreshold(stock.getMinThreshold())
                .build();
    }
}