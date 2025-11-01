package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface CompleteServiceOrderUseCase {
    ServiceOrder handle(CompleteServiceOrderCommand command);
}