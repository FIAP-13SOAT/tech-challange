package com.fiapchallenge.garage.adapters.outbound.repositories.servicetype;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import com.fiapchallenge.garage.shared.mapper.ServiceTypeMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ServiceTypeRepositoryImpl implements ServiceTypeRepository {

    private final JpaServiceTypeRepository jpaServiceTypeRepository;
    private final ServiceTypeMapper serviceTypeMapper;

    public ServiceTypeRepositoryImpl(JpaServiceTypeRepository jpaServiceTypeRepository, ServiceTypeMapper serviceTypeMapper) {
        this.jpaServiceTypeRepository = jpaServiceTypeRepository;
        this.serviceTypeMapper = serviceTypeMapper;
    }

    @Override
    public ServiceType save(ServiceType serviceType) {
        ServiceTypeEntity serviceTypeEntity = serviceTypeMapper.toEntity(serviceType);
        serviceTypeEntity = jpaServiceTypeRepository.save(serviceTypeEntity);
        return serviceTypeMapper.toDomain(serviceTypeEntity);
    }

    @Override
    public ServiceType findByIdOrThrow(UUID serviceTypeId) {
        ServiceTypeEntity entity = jpaServiceTypeRepository.findById(serviceTypeId).orElseThrow();
        return serviceTypeMapper.toDomain(entity);
    }

    @Override
    public List<ServiceType> findAll() {
        return jpaServiceTypeRepository.findAll().stream()
                .map(serviceTypeMapper::toDomain)
                .toList();
    }

}
