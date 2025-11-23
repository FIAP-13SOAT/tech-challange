package com.fiapchallenge.garage.application.serviceorder.cancel;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface CancelServiceOrderUseCase {
    ServiceOrder handle(CancelServiceOrderCommand command);
}
