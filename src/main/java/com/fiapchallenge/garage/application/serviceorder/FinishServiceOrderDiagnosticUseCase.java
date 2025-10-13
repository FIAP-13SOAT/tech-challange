package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;

public interface FinishServiceOrderDiagnosticUseCase {

    void handle(FinishServiceOrderDiagnosticCommand command);
}
