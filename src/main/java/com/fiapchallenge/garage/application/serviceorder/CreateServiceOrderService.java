package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CreateServiceOrderService implements CreateServiceOrderUseCase {

    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceOrderRepository serviceOrderRepository;
    private final ConsumeStockUseCase consumeStockUseCase;

    public CreateServiceOrderService(ServiceTypeRepository serviceTypeRepository, 
                                   ServiceOrderRepository serviceOrderRepository,
                                   ConsumeStockUseCase consumeStockUseCase) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceOrderRepository = serviceOrderRepository;
        this.consumeStockUseCase = consumeStockUseCase;
    }

    @Override
    public ServiceOrder handle(CreateServiceOrderCommand command) {
        List<ServiceType> serviceTypesList = command.serviceTypeIdList()
                .stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList();

        // Consume stock items
        if (command.stockItems() != null && !command.stockItems().isEmpty()) {
            command.stockItems().forEach(item -> {
                ConsumeStockCommand consumeCommand = new ConsumeStockCommand(item.stockId(), item.quantity());
                consumeStockUseCase.handle(consumeCommand);
            });
        }

        List<ServiceOrderItem> stockItems = command.stockItems() != null ?
                command.stockItems().stream()
                        .map(item -> new ServiceOrderItem(item.stockId(), item.quantity()))
                        .toList() : List.of();

        ServiceOrder serviceOrder = new ServiceOrder(command);
        serviceOrder.setServiceTypeList(serviceTypesList);
        serviceOrder.setStockItems(stockItems);
        serviceOrderRepository.save(serviceOrder);

        return serviceOrder;
    }
}
