package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.GetServiceOrderDetailsCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class GetServiceOrderDetailsService implements GetServiceOrderDetailsUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public GetServiceOrderDetailsService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder handle(GetServiceOrderDetailsCommand command) {
        return serviceOrderRepository.findByIdOrThrow(command.id());
    }
}