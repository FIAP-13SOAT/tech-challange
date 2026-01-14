package com.fiapchallenge.garage.application.serviceorder.cancel;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.stock.add.AddStockUseCase;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.application.stock.command.AddStockCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelServiceOrderService implements CancelServiceOrderUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final AddStockUseCase addStockUseCase;

    public CancelServiceOrderService(ServiceOrderRepository serviceOrderRepository, AddStockUseCase addStockUseCase) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.addStockUseCase = addStockUseCase;
    }

    @Override
    public ServiceOrder handle(CancelServiceOrderCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.serviceOrderId())
                .orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

        if (serviceOrder.getStockItems() != null && !serviceOrder.getStockItems().isEmpty()) {
            for (var item : serviceOrder.getStockItems()) {
                AddStockCommand addCommand = new AddStockCommand(item.getStockId(), item.getQuantity());
                addStockUseCase.handle(addCommand);
            }
        }

        serviceOrder.cancel();

        return serviceOrderRepository.save(serviceOrder);
    }
}
