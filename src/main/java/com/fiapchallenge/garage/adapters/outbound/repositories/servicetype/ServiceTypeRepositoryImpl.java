package com.fiapchallenge.garage.adapters.outbound.repositories.servicetype;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.application.servicetype.exceptions.ServiceTypeNotFoundException;
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
        ServiceTypeEntity entity = jpaServiceTypeRepository.findById(serviceTypeId).orElseThrow(() -> new ServiceTypeNotFoundException(serviceTypeId));
        return serviceTypeMapper.toDomain(entity);
    }

    @Override
    public List<ServiceType> findAll() {
        return jpaServiceTypeRepository.findAll().stream()
                .map(serviceTypeMapper::toDomain)
                .toList();
    }

    @Override
    public java.util.Optional<ServiceType> findById(UUID id) {
        return jpaServiceTypeRepository.findById(id)
                .map(serviceTypeMapper::toDomain);
    }

    @Override
    public boolean exists(UUID id) {
        return jpaServiceTypeRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        jpaServiceTypeRepository.deleteById(id);
    }
}
