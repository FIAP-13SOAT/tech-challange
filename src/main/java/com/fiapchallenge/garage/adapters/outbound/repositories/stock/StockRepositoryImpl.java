package com.fiapchallenge.garage.adapters.outbound.repositories.stock;

import com.fiapchallenge.garage.adapters.outbound.entities.StockEntity;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class StockRepositoryImpl implements StockRepository {

    private final JpaStockRepository jpaStockRepository;

    public StockRepositoryImpl(JpaStockRepository jpaStockRepository) {
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

    @Override
    public Optional<Stock> findByProductName(String productName) {
        return jpaStockRepository.findByProductName(productName).map(this::toDomain);
    }

    private StockEntity toEntity(Stock stock) {
        return new StockEntity(
                stock.getId(),
                stock.getProductName(),
                stock.getDescription(),
                stock.getQuantity(),
                stock.getUnitPrice(),
                stock.getCategory(),
                stock.getCreatedAt(),
                stock.getUpdatedAt()
        );
    }

    private Stock toDomain(StockEntity entity) {
        return new Stock(
                entity.getId(),
                entity.getProductName(),
                entity.getDescription(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getCategory(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}