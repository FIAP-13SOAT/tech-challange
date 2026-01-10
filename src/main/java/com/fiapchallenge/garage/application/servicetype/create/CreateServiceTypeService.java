package com.fiapchallenge.garage.application.servicetype.create;

import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.ServiceTypeRepositoryImpl;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateServiceTypeService implements CreateServiceTypeUseCase {

    private final ServiceTypeRepositoryImpl serviceTypeRepository;

    public CreateServiceTypeService(ServiceTypeRepositoryImpl serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceType handle(CreateServiceTypeCommand command) {
        ServiceType serviceType = new ServiceType(command);

        return serviceTypeRepository.save(serviceType);
    }
}
