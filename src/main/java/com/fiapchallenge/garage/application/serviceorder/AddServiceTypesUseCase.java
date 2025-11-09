package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.AddServiceTypesCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface AddServiceTypesUseCase {

    ServiceOrder handle(AddServiceTypesCommand command);
}