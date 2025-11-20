package com.fiapchallenge.garage.application.serviceorder.complete;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface CompleteServiceOrderUseCase {
    ServiceOrder handle(CompleteServiceOrderCommand command);
}
