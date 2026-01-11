package com.fiapchallenge.garage.application.servicetype.create;

import com.fiapchallenge.garage.domain.servicetype.ServiceType;

public interface CreateServiceTypeUseCase {

    ServiceType handle(CreateServiceTypeCommand command);
}
