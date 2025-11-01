package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CancelServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface CancelServiceOrderUseCase {
    ServiceOrder handle(CancelServiceOrderCommand command);
}