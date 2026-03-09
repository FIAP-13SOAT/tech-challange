package com.fiapchallenge.garage.application.servicetype.create;

import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateServiceTypeService implements CreateServiceTypeUseCase {

    private final ServiceTypeGateway serviceTypeGateway;

    public CreateServiceTypeService(ServiceTypeGateway serviceTypeGateway) {
        this.serviceTypeGateway = serviceTypeGateway;
    }

    @Override
    public ServiceType handle(CreateServiceTypeCommand command) {
        ServiceType serviceType = new ServiceType(command);

        return serviceTypeGateway.save(serviceType);
    }
}
