package com.fiapchallenge.garage.shared.mapper;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeMapper extends BaseMapper<ServiceType, ServiceTypeEntity> {

    @Override
    public ServiceType toDomain(ServiceTypeEntity entity) {
        return new ServiceType(
                entity.getId(),
                entity.getValue(),
                entity.getDescription()
        );
    }

    @Override
    public ServiceTypeEntity toEntity(ServiceType serviceType) {
        return new ServiceTypeEntity(serviceType);
    }
}