package com.fiapchallenge.garage.domain.servicetype;

import java.util.UUID;

public interface ServiceTypeRepository {

    ServiceType save(ServiceType serviceType);

    ServiceType findByIdOrThrow(UUID serviceTypeId);
}