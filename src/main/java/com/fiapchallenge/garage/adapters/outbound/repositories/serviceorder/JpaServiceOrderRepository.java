package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaServiceOrderRepository extends JpaRepository<ServiceOrderEntity, UUID> {

    @Query("SELECT s FROM ServiceOrderEntity s WHERE s.priority > 0 ORDER BY s.priority ASC, s.id ASC")
    List<ServiceOrderEntity> findActiveOrdersByPriority();
}
