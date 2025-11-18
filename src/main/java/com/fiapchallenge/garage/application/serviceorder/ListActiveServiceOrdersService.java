package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListActiveServiceOrdersService implements ListActiveServiceOrdersUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public ListActiveServiceOrdersService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public List<ServiceOrder> handle() {
        return serviceOrderRepository.findActiveOrdersByPriority();
    }
}