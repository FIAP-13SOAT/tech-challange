package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface FinishServiceOrderDiagnosticUseCase {

    ServiceOrder handle(FinishServiceOrderDiagnosticCommand command);
}
