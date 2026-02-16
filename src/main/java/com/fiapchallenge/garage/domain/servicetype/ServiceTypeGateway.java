package com.fiapchallenge.garage.domain.servicetype;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceTypeGateway {

    ServiceType save(ServiceType serviceType);

    ServiceType findByIdOrThrow(UUID serviceTypeId);

    List<ServiceType> findAll();

    Optional<ServiceType> findById(UUID id);

    boolean exists(UUID id);

    void deleteById(UUID id);
}
