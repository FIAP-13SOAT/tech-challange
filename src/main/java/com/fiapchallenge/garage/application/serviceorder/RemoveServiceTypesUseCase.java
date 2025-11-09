package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.RemoveServiceTypesCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface RemoveServiceTypesUseCase {

    ServiceOrder handle(RemoveServiceTypesCommand command);
}