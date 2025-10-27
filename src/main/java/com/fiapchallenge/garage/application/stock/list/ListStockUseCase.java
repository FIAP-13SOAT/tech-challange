package com.fiapchallenge.garage.application.stock.list;

import com.fiapchallenge.garage.domain.stock.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListStockUseCase {
    Page<Stock> handle(Pageable pageable);
}