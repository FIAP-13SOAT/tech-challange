package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface StartServiceOrderExecutionUseCase {

    ServiceOrder handle(StartServiceOrderExecutionCommand command);
}
