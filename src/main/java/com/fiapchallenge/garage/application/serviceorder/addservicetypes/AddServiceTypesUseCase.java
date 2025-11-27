package com.fiapchallenge.garage.application.serviceorder.addservicetypes;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface AddServiceTypesUseCase {

    ServiceOrder handle(AddServiceTypesCommand command);
}
