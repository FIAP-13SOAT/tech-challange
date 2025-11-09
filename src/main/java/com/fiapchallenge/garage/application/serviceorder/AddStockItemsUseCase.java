package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.AddStockItemsCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface AddStockItemsUseCase {

    ServiceOrder handle(AddStockItemsCommand command);
}