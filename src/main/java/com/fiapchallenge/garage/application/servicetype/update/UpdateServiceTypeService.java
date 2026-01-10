package com.fiapchallenge.garage.application.servicetype.update;

import com.fiapchallenge.garage.application.servicetype.exceptions.ServiceTypeNotFoundException;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateServiceTypeService implements UpdateServiceTypeUseCase {

    private final ServiceTypeRepository serviceTypeRepository;

    public UpdateServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceType handle(UpdateServiceTypeCmd cmd) {
        ServiceType serviceType = serviceTypeRepository.findById(cmd.id())
            .orElseThrow(() -> new ServiceTypeNotFoundException(cmd.id()));

        serviceType.setDescription(cmd.description());
        serviceType.setValue(cmd.value());

        return serviceTypeRepository.save(serviceType);
    }
}
