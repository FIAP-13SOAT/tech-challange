package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.quote.CreateQuoteUseCase;
import com.fiapchallenge.garage.application.quote.command.CreateQuoteCommand;
import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.shared.service.BaseServiceOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FinishServiceOrderDiagnosticService extends BaseServiceOrderService implements FinishServiceOrderDiagnosticUseCase {

    private final CreateQuoteUseCase createQuoteUseCase;

    public FinishServiceOrderDiagnosticService(ServiceOrderRepository serviceOrderRepository, CreateQuoteUseCase createQuoteUseCase) {
        super(serviceOrderRepository);
        this.createQuoteUseCase = createQuoteUseCase;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = executeServiceOrderOperation(command.id(), ServiceOrder::sendToApproval);
        createQuoteUseCase.handle(new CreateQuoteCommand(serviceOrder));
        return serviceOrder;
    }
}
