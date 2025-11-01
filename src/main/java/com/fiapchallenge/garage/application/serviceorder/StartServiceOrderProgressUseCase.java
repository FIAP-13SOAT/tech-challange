package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderProgressCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public interface StartServiceOrderProgressUseCase {
    ServiceOrder handle(StartServiceOrderProgressCommand command);
}