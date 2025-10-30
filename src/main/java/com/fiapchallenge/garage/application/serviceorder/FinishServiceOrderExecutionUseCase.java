package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface FinishServiceOrderExecutionUseCase {

    ServiceOrder handle(FinishServiceOrderExecutionCommand command);
}
