package com.fiapchallenge.garage.application.serviceorder.deliver;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeliverServiceOrderService implements DeliverServiceOrderUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public DeliverServiceOrderService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder handle(DeliverServiceOrderCommand command) {
        ServiceOrder serviceOrder = this.serviceOrderRepository.findById(command.serviceOrderId()).orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

        serviceOrder.deliver();

        return this.serviceOrderRepository.save(serviceOrder);
    }
}
