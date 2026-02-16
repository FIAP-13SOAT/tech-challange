package com.fiapchallenge.garage.application.serviceorder.list;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListActiveServiceOrdersService implements ListActiveServiceOrdersUseCase {

    private final ServiceOrderGateway serviceOrderGateway;

    public ListActiveServiceOrdersService(ServiceOrderGateway serviceOrderGateway) {
        this.serviceOrderGateway = serviceOrderGateway;
    }

    @Override
    public List<ServiceOrder> handle() {
        return serviceOrderGateway.findActiveOrdersByPriority();
    }
}
