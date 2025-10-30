package com.fiapchallenge.garage.domain.serviceorderexecution;

import java.util.Optional;
import java.util.UUID;

public interface ServiceOrderExecutionRepository {

    void save(ServiceOrderExecution serviceOrderExecution);

    Optional<ServiceOrderExecution> findById(UUID id);



}
