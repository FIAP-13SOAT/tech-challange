package com.fiapchallenge.garage.application.serviceorder.create;

import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerGateway;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.application.customer.exceptions.CustomerNotFoundException;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import com.fiapchallenge.garage.application.stock.command.ConsumeStockCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CreateServiceOrderService implements CreateServiceOrderUseCase {

    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceOrderGateway serviceOrderGateway;
    private final ConsumeStockUseCase consumeStockUseCase;
    private final CustomerGateway customerGateway;

    public CreateServiceOrderService(ServiceTypeRepository serviceTypeRepository,
                                   ServiceOrderGateway serviceOrderGateway,
                                   ConsumeStockUseCase consumeStockUseCase,
                                   CustomerGateway customerGateway) {

        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceOrderGateway = serviceOrderGateway;
        this.consumeStockUseCase = consumeStockUseCase;
        this.customerGateway = customerGateway;
    }

    @Override
    public ServiceOrder handle(CreateServiceOrderCommand command) {
        Customer customer = customerGateway.findById(command.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(command.customerId()));

        List<ServiceType> serviceTypesList = command.serviceTypeIdList()
                .stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList();

        command.stockItems().forEach(item -> {
            ConsumeStockCommand consumeCommand = new ConsumeStockCommand(item.stockId(), item.quantity());
            consumeStockUseCase.handle(consumeCommand);
        });

        List<ServiceOrderItem> stockItems = command.stockItems().stream()
                        .map(item -> new ServiceOrderItem(item.stockId(), item.quantity()))
                        .toList();

        ServiceOrder serviceOrder = new ServiceOrder(command, customer);
        serviceOrder.setServiceTypeList(serviceTypesList);
        serviceOrder.setStockItems(stockItems);

        return serviceOrderGateway.save(serviceOrder);
    }
}
