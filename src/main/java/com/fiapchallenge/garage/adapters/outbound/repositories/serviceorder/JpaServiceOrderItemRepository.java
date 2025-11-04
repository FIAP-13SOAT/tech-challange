package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaServiceOrderItemRepository extends JpaRepository<ServiceOrderItemEntity, UUID> {
    List<ServiceOrderItemEntity> findByServiceOrderId(UUID serviceOrderId);
    void deleteByServiceOrderId(UUID serviceOrderId);
}