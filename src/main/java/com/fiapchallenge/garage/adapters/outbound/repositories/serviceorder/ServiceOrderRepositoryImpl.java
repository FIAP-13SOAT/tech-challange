package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderItemEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ServiceOrderRepositoryImpl implements ServiceOrderRepository {

    private final JpaServiceOrderRepository jpaServiceOrderRepository;
    private final JpaServiceTypeRepository jpaServiceTypeRepository;
    private final JpaServiceOrderItemRepository jpaServiceOrderItemRepository;

    public ServiceOrderRepositoryImpl(JpaServiceOrderRepository jpaServiceOrderRepository,
                                     JpaServiceTypeRepository jpaServiceTypeRepository,
                                     JpaServiceOrderItemRepository jpaServiceOrderItemRepository) {

        this.jpaServiceOrderRepository = jpaServiceOrderRepository;
        this.jpaServiceTypeRepository = jpaServiceTypeRepository;
        this.jpaServiceOrderItemRepository = jpaServiceOrderItemRepository;
    }

    @Transactional
    public ServiceOrder save(ServiceOrder serviceOrder) {
        List<ServiceTypeEntity> serviceTypeEntities = jpaServiceTypeRepository.findAllById(serviceOrder.getServiceTypeListIds());

        ServiceOrderEntity serviceOrderEntity = new ServiceOrderEntity(serviceOrder);
        if (serviceOrder.getId() != null) {
            serviceOrderEntity.setId(serviceOrder.getId());
        }
        serviceOrderEntity.setServiceTypeList(serviceTypeEntities);

        ServiceOrderEntity savedEntity = jpaServiceOrderRepository.save(serviceOrderEntity);

        if (serviceOrder.getId() != null) {
            jpaServiceOrderItemRepository.deleteByServiceOrderId(savedEntity.getId());
        }

        if (serviceOrder.getStockItems() != null && !serviceOrder.getStockItems().isEmpty()) {
            List<ServiceOrderItemEntity> stockItemEntities = serviceOrder.getStockItems().stream()
                    .map(item -> new ServiceOrderItemEntity(savedEntity.getId(), item.getStockId(), item.getQuantity()))
                    .toList();
            jpaServiceOrderItemRepository.saveAll(stockItemEntities);
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
        ServiceOrderEntity entity = jpaServiceOrderRepository.findById(id).orElseThrow(() -> new SoatNotFoundException("Ordem de serviço não encontrado"));
        return convertFromEntity(entity);
    }

    private ServiceOrder convertFromEntity(ServiceOrderEntity serviceOrderEntity) {
        List<ServiceType> serviceTypeList = new ArrayList<>(serviceOrderEntity.getServiceTypeList().stream().map(it ->
                new ServiceType(it.getId(), it.getValue(), it.getDescription())
        ).toList());

        List<ServiceOrderItemEntity> stockItemEntities = jpaServiceOrderItemRepository.findByServiceOrderId(serviceOrderEntity.getId());
        List<ServiceOrderItem> stockItems = new ArrayList<>(stockItemEntities.stream()
                .map(item -> new ServiceOrderItem(item.getId(), item.getStockId(), item.getQuantity()))
                .toList());

        return new ServiceOrder(
                serviceOrderEntity.getId(),
                serviceOrderEntity.getObservations(),
                serviceOrderEntity.getVehicleId(),
                serviceOrderEntity.getCustomerId(),
                serviceOrderEntity.getStatus(),
                serviceTypeList,
                stockItems
        );
    }
}
