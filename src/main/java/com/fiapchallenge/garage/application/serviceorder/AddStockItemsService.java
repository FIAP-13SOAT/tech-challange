package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.AddStockItemsCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AddStockItemsService implements AddStockItemsUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public AddStockItemsService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder handle(AddStockItemsCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(command.serviceOrderId());
        List<ServiceOrderItem> items = command.stockItems().stream()
                .map(item -> new ServiceOrderItem(null, item.stockId(), item.quantity()))
                .toList();
        serviceOrder.addStockItems(items);
        return serviceOrderRepository.save(serviceOrder);
    }
}