package com.fiapchallenge.garage.application.serviceorderexecution;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.serviceorderexecution.exceptions.ServiceOrderExecutionNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishServiceOrderExecutionService implements FinishServiceOrderExecutionUseCase {

    private final ServiceOrderExecutionGateway serviceOrderExecutionGateway;
    private final ServiceOrderGateway serviceOrderGateway;

    public FinishServiceOrderExecutionService(
            ServiceOrderGateway serviceOrderGateway,
            ServiceOrderExecutionGateway serviceOrderExecutionGateway
    ) {
        this.serviceOrderExecutionGateway = serviceOrderExecutionGateway;
        this.serviceOrderGateway = serviceOrderGateway;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderExecutionCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findById(command.id())
                .orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

        ServiceOrderExecution serviceOrderExecution = serviceOrderExecutionGateway.findById(command.id())
                .orElseThrow(() -> new ServiceOrderExecutionNotFoundException(command.id()));

        serviceOrderExecution.finish();
        serviceOrderExecutionGateway.save(serviceOrderExecution);

        return serviceOrder;
    }
}
