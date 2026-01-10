package com.fiapchallenge.garage.application.serviceorder.create;

import com.fiapchallenge.garage.application.stock.consume.ConsumeStockUseCase;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
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
    private final ServiceOrderRepository serviceOrderRepository;
    private final ConsumeStockUseCase consumeStockUseCase;
    private final CustomerRepository customerRepository;

    public CreateServiceOrderService(ServiceTypeRepository serviceTypeRepository,
                                   ServiceOrderRepository serviceOrderRepository,
                                   ConsumeStockUseCase consumeStockUseCase,
                                   CustomerRepository customerRepository) {

        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceOrderRepository = serviceOrderRepository;
        this.consumeStockUseCase = consumeStockUseCase;
        this.customerRepository = customerRepository;
    }

    @Override
    public ServiceOrder handle(CreateServiceOrderCommand command) {
        Customer customer = customerRepository.findById(command.customerId())
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

        return serviceOrderRepository.save(serviceOrder);
    }
}
