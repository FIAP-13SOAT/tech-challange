package com.fiapchallenge.garage.adapters.outbound.repositories.stock;

import com.fiapchallenge.garage.adapters.outbound.entities.StockEntity;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.shared.mapper.StockMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class StockRepositoryImpl implements StockRepository {

    private final JpaStockRepository jpaStockRepository;
    private final StockMapper stockMapper;

    public StockRepositoryImpl(JpaStockRepository jpaStockRepository, StockMapper stockMapper) {
        this.jpaStockRepository = jpaStockRepository;
        this.stockMapper = stockMapper;
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity entity = stockMapper.toEntity(stock);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        StockEntity savedEntity = jpaStockRepository.save(entity);
        return stockMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Stock> findById(UUID id) {
        return jpaStockRepository.findById(id).map(stockMapper::toDomain);
    }

    @Override
    public Page<Stock> findAll(Pageable pageable) {
        return jpaStockRepository.findAll(pageable).map(stockMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaStockRepository.deleteById(id);
    }

}
