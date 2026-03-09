package com.fiapchallenge.garage.adapters.outbound.gateways.servicetype;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.application.servicetype.exceptions.ServiceTypeNotFoundException;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ServiceTypeGatewayImpl implements ServiceTypeGateway {

    private final JpaServiceTypeRepository jpaServiceTypeRepository;

    public ServiceTypeGatewayImpl(JpaServiceTypeRepository jpaServiceTypeRepository) {
        this.jpaServiceTypeRepository = jpaServiceTypeRepository;
    }

    @Override
    public ServiceType save(ServiceType serviceType) {
        ServiceTypeEntity serviceTypeEntity = toEntity(serviceType);
        serviceTypeEntity = jpaServiceTypeRepository.save(serviceTypeEntity);
        return toDomain(serviceTypeEntity);
    }

    @Override
    public ServiceType findByIdOrThrow(UUID serviceTypeId) {
        ServiceTypeEntity entity = jpaServiceTypeRepository.findById(serviceTypeId).orElseThrow(() -> new ServiceTypeNotFoundException(serviceTypeId));
        return toDomain(entity);
    }

    @Override
    public List<ServiceType> findAll() {
        return jpaServiceTypeRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<ServiceType> findById(UUID id) {
        return jpaServiceTypeRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public boolean exists(UUID id) {
        return jpaServiceTypeRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        jpaServiceTypeRepository.deleteById(id);
    }

    private ServiceType toDomain(ServiceTypeEntity entity) {
        return new ServiceType(
                entity.getId(),
                entity.getValue(),
                entity.getDescription()
        );
    }

    private ServiceTypeEntity toEntity(ServiceType serviceType) {
        ServiceTypeEntity entity = new ServiceTypeEntity();
        entity.setId(serviceType.getId());
        entity.setDescription(serviceType.getDescription());
        entity.setValue(serviceType.getValue());
        return entity;
    }
}
