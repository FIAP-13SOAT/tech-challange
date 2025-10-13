package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface StartServiceOrderDiagnosticUseCase {

    ServiceOrder handle(StartServiceOrderDiagnosticCommand command);
}
