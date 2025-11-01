package com.fiapchallenge.garage.adapters.outbound.repositories.servicetype;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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

        return convertFromEntity(serviceTypeEntity);
    }

    @Override
    public ServiceType findByIdOrThrow(UUID serviceTypeId) {
        ServiceTypeEntity entity = jpaServiceTypeRepository.findById(serviceTypeId).orElseThrow();
        return convertFromEntity(entity);
    }

    @Override
    public List<ServiceType> findAll() {
        return jpaServiceTypeRepository.findAll().stream()
                .map(this::convertFromEntity)
                .toList();
    }

    private ServiceType convertFromEntity(ServiceTypeEntity serviceTypeEntity) {
        return new ServiceType(
                serviceTypeEntity.getId(),
                serviceTypeEntity.getValue(),
                serviceTypeEntity.getDescription()
        );
    }
}
