package com.fiapchallenge.garage.application.serviceorder.addservicetypes;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AddServiceTypesService implements AddServiceTypesUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final ServiceTypeGateway serviceTypeGateway;

    public AddServiceTypesService(ServiceOrderGateway serviceOrderGateway,
                                  ServiceTypeGateway serviceTypeGateway) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.serviceTypeGateway = serviceTypeGateway;
    }

    @Override
    public ServiceOrder handle(AddServiceTypesCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findByIdOrThrow(command.serviceOrderId());
        List<ServiceType> serviceTypes = command.serviceTypeIds().stream()
                .map(serviceTypeGateway::findByIdOrThrow)
                .toList();
        serviceOrder.addServiceTypes(serviceTypes);
        return serviceOrderGateway.save(serviceOrder);
    }
}
