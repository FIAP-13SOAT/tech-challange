package com.fiapchallenge.garage.adapters.outbound.repositories.stock;

import com.fiapchallenge.garage.adapters.outbound.entities.StockEntity;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class StockGatewayImpl implements StockGateway {

    private final JpaStockRepository jpaStockRepository;

    public StockGatewayImpl(JpaStockRepository jpaStockRepository) {
        this.jpaStockRepository = jpaStockRepository;
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity entity = toEntity(stock);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        StockEntity savedEntity = jpaStockRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Stock> findById(UUID id) {
        return jpaStockRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Page<Stock> findAll(Pageable pageable) {
        return jpaStockRepository.findAll(pageable).map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaStockRepository.deleteById(id);
    }

    private Stock toDomain(StockEntity entity) {
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

    private StockEntity toEntity(Stock stock) {
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
