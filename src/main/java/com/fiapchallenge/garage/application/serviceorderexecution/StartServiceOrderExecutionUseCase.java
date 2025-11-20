package com.fiapchallenge.garage.application.serviceorderexecution;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface StartServiceOrderExecutionUseCase {

    ServiceOrder handle(StartServiceOrderExecutionCommand command);
}
