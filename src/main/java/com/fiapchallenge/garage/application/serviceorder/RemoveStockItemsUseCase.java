package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.RemoveStockItemsCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface RemoveStockItemsUseCase {

    ServiceOrder handle(RemoveStockItemsCommand command);
}