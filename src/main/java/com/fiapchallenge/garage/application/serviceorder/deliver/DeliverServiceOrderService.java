package com.fiapchallenge.garage.application.serviceorder.deliver;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeliverServiceOrderService implements DeliverServiceOrderUseCase {

    private final ServiceOrderGateway serviceOrderGateway;

    public DeliverServiceOrderService(ServiceOrderGateway serviceOrderGateway) {
        this.serviceOrderGateway = serviceOrderGateway;
    }

    @Override
    public ServiceOrder handle(DeliverServiceOrderCommand command) {
        ServiceOrder serviceOrder = this.serviceOrderGateway.findById(command.serviceOrderId()).orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

        serviceOrder.deliver();

        return this.serviceOrderGateway.save(serviceOrder);
    }
}
