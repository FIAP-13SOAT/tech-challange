package com.fiapchallenge.garage.application.serviceorder.getstatus;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderStatusDTO;

public interface GetServiceOrderStatusUseCase {
    ServiceOrderStatusDTO handle(GetServiceOrderStatusCommand command);
}