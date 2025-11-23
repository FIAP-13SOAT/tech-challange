package com.fiapchallenge.garage.application.serviceorder.removestockitems;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface RemoveStockItemsUseCase {

    ServiceOrder handle(RemoveStockItemsCommand command);
}
