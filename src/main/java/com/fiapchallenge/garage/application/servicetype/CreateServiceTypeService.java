package com.fiapchallenge.garage.application.servicetype;

import com.fiapchallenge.garage.application.servicetype.command.CreateServiceTypeCommand;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateServiceTypeService implements CreateServiceTypeUseCase {

    private final ServiceTypeRepository serviceTypeRepository;

    public CreateServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceType handle(CreateServiceTypeCommand command) {
        ServiceType serviceType = new ServiceType(command);

        return serviceTypeRepository.save(serviceType);
    }
}
