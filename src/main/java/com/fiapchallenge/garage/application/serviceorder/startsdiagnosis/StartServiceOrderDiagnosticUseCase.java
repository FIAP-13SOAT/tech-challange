package com.fiapchallenge.garage.application.serviceorder.startsdiagnosis;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface StartServiceOrderDiagnosticUseCase {

    ServiceOrder handle(StartServiceOrderDiagnosticCommand command);
}
