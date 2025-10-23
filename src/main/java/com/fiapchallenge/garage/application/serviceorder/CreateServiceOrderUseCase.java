package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface CreateServiceOrderUseCase {

    ServiceOrder handle(CreateServiceOrderCommand command);
}
