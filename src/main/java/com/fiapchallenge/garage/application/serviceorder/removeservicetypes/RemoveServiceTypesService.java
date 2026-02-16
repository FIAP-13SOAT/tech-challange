package com.fiapchallenge.garage.application.serviceorder.removeservicetypes;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RemoveServiceTypesService implements RemoveServiceTypesUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final ServiceTypeRepository serviceTypeRepository;

    public RemoveServiceTypesService(ServiceOrderGateway serviceOrderGateway,
                                     ServiceTypeRepository serviceTypeRepository) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceOrder handle(RemoveServiceTypesCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findByIdOrThrow(command.serviceOrderId());
        List<ServiceType> serviceTypes = command.serviceTypeIds().stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList();
        serviceOrder.removeServiceTypes(serviceTypes);
        return serviceOrderGateway.save(serviceOrder);
    }
}
