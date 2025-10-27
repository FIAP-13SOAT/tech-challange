package com.fiapchallenge.garage.application.stock.update;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.command.UpdateStockCommand;

public interface UpdateStockUseCase {
    Stock handle(UpdateStockCommand command);
}