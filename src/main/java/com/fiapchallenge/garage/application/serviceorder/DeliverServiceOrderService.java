package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliverServiceOrderService implements DeliverServiceOrderUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public DeliverServiceOrderService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    @Transactional
    public ServiceOrder handle(DeliverServiceOrderCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(command.serviceOrderId());
        serviceOrder.deliver();
        return serviceOrderRepository.save(serviceOrder);
    }
}