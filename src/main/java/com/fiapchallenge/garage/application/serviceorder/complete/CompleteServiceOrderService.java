package com.fiapchallenge.garage.application.serviceorder.complete;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.serviceorderexecution.FinishServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorderexecution.FinishServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompleteServiceOrderService implements CompleteServiceOrderUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase;

    public CompleteServiceOrderService(ServiceOrderRepository serviceOrderRepository, FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.finishServiceOrderExecutionUseCase = finishServiceOrderExecutionUseCase;
    }

    @Override
    @Transactional
    public ServiceOrder handle(CompleteServiceOrderCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.serviceOrderId())
            .orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

        serviceOrder.complete();

        serviceOrderRepository.save(serviceOrder);

        finishServiceOrderExecutionUseCase.handle(new FinishServiceOrderExecutionCommand(command.serviceOrderId()));

        return serviceOrder;
    }
}
