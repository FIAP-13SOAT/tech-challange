package com.fiapchallenge.garage.adapters.inbound.rest.serviceorderexecution;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.controllers.serviceorderexecution.ServiceOrderExecutionController;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionGateway;
import com.fiapchallenge.garage.presenters.serviceorderexecution.ServiceOrderExecutionPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderExecutionResource implements ServiceOrderExecutionResourceOpenApiSpec {

    private final ServiceOrderExecutionController serviceOrderExecutionController;

    public ServiceOrderExecutionResource(
            StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase,
            ServiceOrderExecutionGateway serviceOrderExecutionGateway
    ) {
        this.serviceOrderExecutionController = new ServiceOrderExecutionController(
                serviceOrderExecutionGateway,
                new ServiceOrderExecutionPresenter(),
                startServiceOrderExecutionUseCase
        );
    }

    @Override
    @PostMapping("/{id}/start-execution")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> startExecution(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderExecutionController.startExecution(id));
    }
}
