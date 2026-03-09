package com.fiapchallenge.garage.application.serviceorder.finishdiagnosis;

import com.fiapchallenge.garage.application.quote.GenerateQuoteUseCase;
import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishServiceOrderDiagnosticService implements FinishServiceOrderDiagnosticUseCase {

    private final ServiceOrderGateway serviceOrderGateway;

    private final GenerateQuoteUseCase generateQuoteUseCase;

    public FinishServiceOrderDiagnosticService(ServiceOrderGateway serviceOrderGateway, GenerateQuoteUseCase generateQuoteUseCase) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.generateQuoteUseCase = generateQuoteUseCase;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = this.serviceOrderGateway.findById(command.id()).orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

        serviceOrder.sendToApproval();
        serviceOrderGateway.save(serviceOrder);
        generateQuoteUseCase.handle(serviceOrder.getId());
        return serviceOrder;
    }
}
