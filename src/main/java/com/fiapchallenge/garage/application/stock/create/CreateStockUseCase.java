package com.fiapchallenge.garage.application.stock.create;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.command.CreateStockCommand;

public interface CreateStockUseCase {
    Stock handle(CreateStockCommand command);
}