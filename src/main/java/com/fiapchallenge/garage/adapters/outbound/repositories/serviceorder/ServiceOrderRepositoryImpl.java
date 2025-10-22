package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

@Component
public class ServiceOrderRepositoryImpl implements ServiceOrderRepository {

    private final JpaServiceOrderRepository jpaServiceOrderRepository;
    private final JpaServiceTypeRepository jpaServiceTypeRepository;

    public ServiceOrderRepositoryImpl(JpaServiceOrderRepository jpaServiceOrderRepository, JpaServiceTypeRepository jpaServiceTypeRepository) {
        this.jpaServiceOrderRepository = jpaServiceOrderRepository;
        this.jpaServiceTypeRepository = jpaServiceTypeRepository;
    }

    public ServiceOrder save(ServiceOrder serviceOrder) {
        List<ServiceTypeEntity> serviceTypeEntities = jpaServiceTypeRepository.findAllById(serviceOrder.getServiceTypeListIds());

        ServiceOrderEntity serviceOrderEntity = new ServiceOrderEntity(serviceOrder);
        serviceOrderEntity.setServiceTypeList(serviceTypeEntities);

        serviceOrderEntity = jpaServiceOrderRepository.save(serviceOrderEntity);

        return convertFromEntity(serviceOrderEntity);
    }

    @Override
    public Optional<ServiceOrder> findById(UUID id) {
        Optional<ServiceOrderEntity> serviceOrderEntity = jpaServiceOrderRepository.findById(id);
        return serviceOrderEntity.map(this::convertFromEntity);
    }

    private ServiceOrder convertFromEntity(ServiceOrderEntity serviceOrderEntity) {
        List<ServiceType> serviceTypeList = serviceOrderEntity.getServiceTypeList().stream().map(it ->
                new ServiceType(it.getId(), it.getValue(), it.getDescription())
        ).toList();

        return new ServiceOrder(
                serviceOrderEntity.getId(),
                serviceOrderEntity.getObservations(),
                serviceOrderEntity.getVehicleId(),
                serviceOrderEntity.getStatus(),
                serviceTypeList
        );
    }
}
