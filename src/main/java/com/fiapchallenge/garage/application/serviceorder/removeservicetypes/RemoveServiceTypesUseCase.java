package com.fiapchallenge.garage.application.serviceorder.removeservicetypes;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface RemoveServiceTypesUseCase {

    ServiceOrder handle(RemoveServiceTypesCommand command);
}
