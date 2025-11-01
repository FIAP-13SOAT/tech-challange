package com.fiapchallenge.garage.application.stock.add;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.command.AddStockCommand;

public interface AddStockUseCase {
    Stock handle(AddStockCommand command);
}