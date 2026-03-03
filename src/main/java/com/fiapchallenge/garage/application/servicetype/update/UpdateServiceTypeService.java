package com.fiapchallenge.garage.application.servicetype.update;

import com.fiapchallenge.garage.application.servicetype.exceptions.ServiceTypeNotFoundException;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateServiceTypeService implements UpdateServiceTypeUseCase {

    private final ServiceTypeGateway serviceTypeGateway;

    public UpdateServiceTypeService(ServiceTypeGateway serviceTypeGateway) {
        this.serviceTypeGateway = serviceTypeGateway;
    }

    @Override
    public ServiceType handle(UpdateServiceTypeCmd cmd) {
        ServiceType serviceType = serviceTypeGateway.findById(cmd.id())
            .orElseThrow(() -> new ServiceTypeNotFoundException(cmd.id()));

        serviceType.setDescription(cmd.description());
        serviceType.setValue(cmd.value());

        return serviceTypeGateway.save(serviceType);
    }
}
