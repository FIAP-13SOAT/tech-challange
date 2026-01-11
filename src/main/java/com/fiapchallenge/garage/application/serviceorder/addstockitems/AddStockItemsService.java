package com.fiapchallenge.garage.application.serviceorder.addstockitems;

import com.fiapchallenge.garage.application.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
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
    private final ConsumeStockUseCase consumeStockUseCase;

    public AddStockItemsService(ServiceOrderRepository serviceOrderRepository, ConsumeStockUseCase consumeStockUseCase) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.consumeStockUseCase = consumeStockUseCase;
    }

    @Override
    public ServiceOrder handle(AddStockItemsCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(command.serviceOrderId());
        
        command.stockItems().forEach(item -> {
            ConsumeStockCommand consumeCommand = new ConsumeStockCommand(item.stockId(), item.quantity());
            consumeStockUseCase.handle(consumeCommand);
        });
        
        List<ServiceOrderItem> items = command.stockItems().stream()
                .map(item -> new ServiceOrderItem(null, item.stockId(), item.quantity()))
                .toList();
        serviceOrder.addStockItems(items);
        return serviceOrderRepository.save(serviceOrder);
    }
}
