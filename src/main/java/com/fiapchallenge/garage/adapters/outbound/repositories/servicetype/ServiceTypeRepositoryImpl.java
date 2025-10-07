package com.fiapchallenge.garage.adapters.outbound.repositories.servicetype;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeRepositoryImpl implements ServiceTypeRepository {

    private final JpaServiceTypeRepository jpaServiceTypeRepository;

    public ServiceTypeRepositoryImpl(JpaServiceTypeRepository jpaServiceTypeRepository) {
        this.jpaServiceTypeRepository = jpaServiceTypeRepository;
    }

    @Override
    public ServiceType save(ServiceType serviceType) {
        ServiceTypeEntity serviceTypeEntity = new ServiceTypeEntity(serviceType);
        serviceTypeEntity = jpaServiceTypeRepository.save(serviceTypeEntity);

        return new ServiceType(
                serviceTypeEntity.getId(),
                serviceTypeEntity.getValue(),
                serviceTypeEntity.getDescription()
        );
    }
}
