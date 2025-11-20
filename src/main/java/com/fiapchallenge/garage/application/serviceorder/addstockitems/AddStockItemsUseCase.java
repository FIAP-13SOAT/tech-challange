package com.fiapchallenge.garage.application.serviceorder.addstockitems;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface AddStockItemsUseCase {

    ServiceOrder handle(AddStockItemsCommand command);
}
