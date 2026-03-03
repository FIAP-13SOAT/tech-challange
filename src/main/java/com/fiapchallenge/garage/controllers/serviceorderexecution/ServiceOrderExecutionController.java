package com.fiapchallenge.garage.controllers.serviceorderexecution;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionCommand;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionGateway;
import com.fiapchallenge.garage.presenters.serviceorderexecution.ServiceOrderExecutionPresenter;

import java.util.UUID;

public class ServiceOrderExecutionController {

    private final ServiceOrderExecutionGateway serviceOrderExecutionGateway;
    private final ServiceOrderExecutionPresenter serviceOrderExecutionPresenter;
    private final StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase;

    public ServiceOrderExecutionController(
            ServiceOrderExecutionGateway serviceOrderExecutionGateway,
            ServiceOrderExecutionPresenter serviceOrderExecutionPresenter,
            StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase
    ) {
        this.serviceOrderExecutionGateway = serviceOrderExecutionGateway;
        this.serviceOrderExecutionPresenter = serviceOrderExecutionPresenter;
        this.startServiceOrderExecutionUseCase = startServiceOrderExecutionUseCase;
    }

    public ServiceOrderResponseDTO startExecution(UUID id) {
        return serviceOrderExecutionPresenter.present(
                startServiceOrderExecutionUseCase.handle(new StartServiceOrderExecutionCommand(id))
        );
    }
}
