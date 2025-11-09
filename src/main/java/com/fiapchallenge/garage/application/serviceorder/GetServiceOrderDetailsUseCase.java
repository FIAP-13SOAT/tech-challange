package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.GetServiceOrderDetailsCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface GetServiceOrderDetailsUseCase {

    ServiceOrder handle(GetServiceOrderDetailsCommand command);
}