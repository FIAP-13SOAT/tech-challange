package com.fiapchallenge.garage.domain.serviceorderexecution;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderExecutionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceOrderExecutionRepository {

    void save(ServiceOrderExecution serviceOrderExecution);

    Optional<ServiceOrderExecution> findById(UUID id);

    List<ServiceOrderExecution> findByStartDateBetweenOrderByStartDateAsc(
            LocalDateTime startRange,
            LocalDateTime endRange
    );

}
