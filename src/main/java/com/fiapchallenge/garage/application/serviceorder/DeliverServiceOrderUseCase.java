package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface DeliverServiceOrderUseCase {
    ServiceOrder handle(DeliverServiceOrderCommand command);
}