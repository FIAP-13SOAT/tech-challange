package com.fiapchallenge.garage.application.serviceorder.complete;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.serviceorderexecution.FinishServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorderexecution.FinishServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.shared.metrics.ServiceOrderMetrics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompleteServiceOrderService implements CompleteServiceOrderUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase;
    private final ServiceOrderMetrics serviceOrderMetrics;

    public CompleteServiceOrderService(ServiceOrderGateway serviceOrderGateway,
                                       FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase,
                                       ServiceOrderMetrics serviceOrderMetrics) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.finishServiceOrderExecutionUseCase = finishServiceOrderExecutionUseCase;
        this.serviceOrderMetrics = serviceOrderMetrics;
    }

    @Override
    @Transactional
    public ServiceOrder handle(CompleteServiceOrderCommand command) {
        long startTime = System.currentTimeMillis();
        try {
            ServiceOrder serviceOrder = serviceOrderGateway.findById(command.serviceOrderId())
                .orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

            String fromStatus = serviceOrder.getStatus().name();
            serviceOrder.complete();
            String toStatus = serviceOrder.getStatus().name();

            serviceOrderGateway.save(serviceOrder);

            finishServiceOrderExecutionUseCase.handle(new FinishServiceOrderExecutionCommand(command.serviceOrderId()));

            serviceOrderMetrics.incrementStatusChange(fromStatus, toStatus);
            serviceOrderMetrics.recordProcessingDuration("complete", "success", System.currentTimeMillis() - startTime);

            return serviceOrder;
        } catch (Exception e) {
            serviceOrderMetrics.incrementError("complete", e.getClass().getSimpleName());
            serviceOrderMetrics.recordProcessingDuration("complete", "error", System.currentTimeMillis() - startTime);
            throw e;
        }
    }
}
