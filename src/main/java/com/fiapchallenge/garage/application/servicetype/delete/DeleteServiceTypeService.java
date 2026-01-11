package com.fiapchallenge.garage.application.servicetype.delete;

import com.fiapchallenge.garage.application.servicetype.exceptions.ServiceTypeNotFoundException;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DeleteServiceTypeService implements DeleteServiceTypeUseCase {

    private final ServiceTypeRepository serviceTypeRepository;

    public DeleteServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public void handle(DeleteServiceTypeCmd cmd) {
        UUID id = cmd.id();

        if (!serviceTypeRepository.exists(id)) {
            throw new ServiceTypeNotFoundException(id);
        }

        serviceTypeRepository.deleteById(id);
    }
}
