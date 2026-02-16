package com.fiapchallenge.garage.application.serviceorder.get;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;

@Service
public class GetServiceOrderDetailsService implements GetServiceOrderDetailsUseCase {

    private final ServiceOrderGateway serviceOrderGateway;

    public GetServiceOrderDetailsService(ServiceOrderGateway serviceOrderGateway) {
        this.serviceOrderGateway = serviceOrderGateway;
    }

    @Override
    public ServiceOrder handle(GetServiceOrderDetailsCommand command) {
        return serviceOrderGateway.findByIdOrThrow(command.id());
    }
}
