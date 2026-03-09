package com.fiapchallenge.garage.application.serviceorder.removestockitems;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RemoveStockItemsService implements RemoveStockItemsUseCase {

    private final ServiceOrderGateway serviceOrderGateway;

    public RemoveStockItemsService(ServiceOrderGateway serviceOrderGateway) {
        this.serviceOrderGateway = serviceOrderGateway;
    }

    @Override
    public ServiceOrder handle(RemoveStockItemsCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findByIdOrThrow(command.serviceOrderId());
        List<ServiceOrderItem> items = command.stockItems().stream()
                .map(item -> new ServiceOrderItem(null, item.stockId(), item.quantity()))
                .toList();
        serviceOrder.removeStockItems(items);
        return serviceOrderGateway.save(serviceOrder);
    }
}
