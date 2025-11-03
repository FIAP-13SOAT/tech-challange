package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.CancelServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.CompleteServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.DeliverServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.FinishServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderDiagnosticUseCase;
import org.springframework.stereotype.Component;

@Component
public class ServiceOrderUseCases {
    private final CreateServiceOrderUseCase createServiceOrderUseCase;
    private final StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase;
    private final FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase;
    private final CompleteServiceOrderUseCase completeServiceOrderUseCase;
    private final DeliverServiceOrderUseCase deliverServiceOrderUseCase;
    private final CancelServiceOrderUseCase cancelServiceOrderUseCase;

    public ServiceOrderUseCases(CreateServiceOrderUseCase createServiceOrderUseCase,
                               StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase,
                               FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase,
                               CompleteServiceOrderUseCase completeServiceOrderUseCase,
                               DeliverServiceOrderUseCase deliverServiceOrderUseCase,
                               CancelServiceOrderUseCase cancelServiceOrderUseCase) {
        this.createServiceOrderUseCase = createServiceOrderUseCase;
        this.startServiceOrderDiagnosticUseCase = startServiceOrderDiagnosticUseCase;
        this.finishServiceOrderDiagnosticUseCase = finishServiceOrderDiagnosticUseCase;
        this.completeServiceOrderUseCase = completeServiceOrderUseCase;
        this.deliverServiceOrderUseCase = deliverServiceOrderUseCase;
        this.cancelServiceOrderUseCase = cancelServiceOrderUseCase;
    }

    public CreateServiceOrderUseCase getCreateServiceOrderUseCase() { return createServiceOrderUseCase; }
    public StartServiceOrderDiagnosticUseCase getStartServiceOrderDiagnosticUseCase() { return startServiceOrderDiagnosticUseCase; }
    public FinishServiceOrderDiagnosticUseCase getFinishServiceOrderDiagnosticUseCase() { return finishServiceOrderDiagnosticUseCase; }
    public CompleteServiceOrderUseCase getCompleteServiceOrderUseCase() { return completeServiceOrderUseCase; }
    public DeliverServiceOrderUseCase getDeliverServiceOrderUseCase() { return deliverServiceOrderUseCase; }
    public CancelServiceOrderUseCase getCancelServiceOrderUseCase() { return cancelServiceOrderUseCase; }
}