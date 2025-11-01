package com.fiapchallenge.garage.application.stock.consume;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;

public interface ConsumeStockUseCase {
    Stock handle(ConsumeStockCommand command);
}
