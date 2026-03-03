package com.fiapchallenge.garage.application.servicetype.delete;

import com.fiapchallenge.garage.application.servicetype.exceptions.ServiceTypeNotFoundException;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DeleteServiceTypeService implements DeleteServiceTypeUseCase {

    private final ServiceTypeGateway serviceTypeGateway;

    public DeleteServiceTypeService(ServiceTypeGateway serviceTypeGateway) {
        this.serviceTypeGateway = serviceTypeGateway;
    }

    @Override
    public void handle(DeleteServiceTypeCmd cmd) {
        UUID id = cmd.id();

        if (!serviceTypeGateway.exists(id)) {
            throw new ServiceTypeNotFoundException(id);
        }

        serviceTypeGateway.deleteById(id);
    }
}
