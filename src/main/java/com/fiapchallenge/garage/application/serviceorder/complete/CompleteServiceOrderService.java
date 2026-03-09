package com.fiapchallenge.garage.application.serviceorder.complete;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.serviceorderexecution.FinishServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorderexecution.FinishServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompleteServiceOrderService implements CompleteServiceOrderUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase;

    public CompleteServiceOrderService(ServiceOrderGateway serviceOrderGateway, FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.finishServiceOrderExecutionUseCase = finishServiceOrderExecutionUseCase;
    }

    @Override
    @Transactional
    public ServiceOrder handle(CompleteServiceOrderCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findById(command.serviceOrderId())
            .orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

        serviceOrder.complete();

        serviceOrderGateway.save(serviceOrder);

        finishServiceOrderExecutionUseCase.handle(new FinishServiceOrderExecutionCommand(command.serviceOrderId()));

        return serviceOrder;
    }
}
