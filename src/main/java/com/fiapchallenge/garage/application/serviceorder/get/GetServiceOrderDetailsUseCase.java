package com.fiapchallenge.garage.application.serviceorder.get;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface GetServiceOrderDetailsUseCase {

    ServiceOrder handle(GetServiceOrderDetailsCommand command);
}
