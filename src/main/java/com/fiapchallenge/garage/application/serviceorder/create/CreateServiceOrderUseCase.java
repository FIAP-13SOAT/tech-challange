package com.fiapchallenge.garage.application.serviceorder.create;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface CreateServiceOrderUseCase {

    ServiceOrder handle(CreateServiceOrderCommand command);
}
