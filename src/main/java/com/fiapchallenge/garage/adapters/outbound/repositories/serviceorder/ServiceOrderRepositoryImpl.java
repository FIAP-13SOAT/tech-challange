package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderItemEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        ServiceOrderEntity savedEntity = jpaServiceOrderRepository.save(serviceOrderEntity);

        if (serviceOrder.getStockItems() != null && !serviceOrder.getStockItems().isEmpty()) {
            List<ServiceOrderItemEntity> stockItemEntities = serviceOrder.getStockItems().stream()
                    .map(item -> new ServiceOrderItemEntity(savedEntity.getId(), item.getStockId(), item.getQuantity()))
                    .collect(Collectors.toList());
            savedEntity.setStockItems(stockItemEntities);
        }

        return convertFromEntity(savedEntity);
    }

    @Override
    public Optional<ServiceOrder> findById(UUID id) {
        Optional<ServiceOrderEntity> serviceOrderEntity = jpaServiceOrderRepository.findById(id);
        return serviceOrderEntity.map(this::convertFromEntity);
    }

    @Override
    public ServiceOrder findByIdOrThrow(UUID id) {
        ServiceOrderEntity entity = jpaServiceOrderRepository.findById(id).orElseThrow();
        return convertFromEntity(entity);
    }

    private ServiceOrder convertFromEntity(ServiceOrderEntity serviceOrderEntity) {
        List<ServiceType> serviceTypeList = serviceOrderEntity.getServiceTypeList().stream().map(it ->
                new ServiceType(it.getId(), it.getValue(), it.getDescription())
        ).collect(Collectors.toList());

        List<ServiceOrderItem> stockItems = serviceOrderEntity.getStockItems() != null ?
                serviceOrderEntity.getStockItems().stream()
                        .map(item -> new ServiceOrderItem(item.getId(), item.getStockId(), item.getQuantity()))
                        .collect(Collectors.toList()) : new java.util.ArrayList<>();

        return new ServiceOrder(
                serviceOrderEntity.getId(),
                serviceOrderEntity.getObservations(),
                serviceOrderEntity.getVehicleId(),
                serviceOrderEntity.getStatus(),
                serviceTypeList,
                stockItems
        );
    }
}
