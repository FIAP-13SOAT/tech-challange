package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorderexecution;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaServiceOrderExecutionRepository extends JpaRepository<ServiceOrderExecutionEntity, UUID> {

    List<ServiceOrderExecutionEntity> findByStartDateBetweenOrderByStartDateAsc(
            LocalDateTime startRange,
            LocalDateTime endRange
    );
}
