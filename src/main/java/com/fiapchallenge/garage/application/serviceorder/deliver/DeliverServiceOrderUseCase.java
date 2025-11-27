package com.fiapchallenge.garage.application.serviceorder.deliver;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface DeliverServiceOrderUseCase {
    ServiceOrder handle(DeliverServiceOrderCommand command);
}
