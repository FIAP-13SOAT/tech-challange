package com.fiapchallenge.garage.application.serviceorder.finishdiagnosis;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface FinishServiceOrderDiagnosticUseCase {

    ServiceOrder handle(FinishServiceOrderDiagnosticCommand command);
}
