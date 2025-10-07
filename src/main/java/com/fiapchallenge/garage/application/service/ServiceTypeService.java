package com.fiapchallenge.garage.application.service;

import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.ServiceTypeRepositoryImpl;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceTypeService {

    private final ServiceTypeRepositoryImpl serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepositoryImpl serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public ServiceType create(ServiceTypeRequestDTO serviceTypeRequestDTO) {
        ServiceType serviceType = new ServiceType(serviceTypeRequestDTO);

        return serviceTypeRepository.save(serviceType);
    }
}
