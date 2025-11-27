package com.fiapchallenge.garage.application.serviceorderexecution;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface FinishServiceOrderExecutionUseCase {

    ServiceOrder handle(FinishServiceOrderExecutionCommand command);
}
