package com.fiapchallenge.garage.application.serviceorderexecution;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionGateway;
import com.fiapchallenge.garage.shared.metrics.ServiceOrderMetrics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartServiceOrderExecutionService implements StartServiceOrderExecutionUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final ServiceOrderExecutionGateway serviceOrderExecutionGateway;
    private final ServiceOrderMetrics serviceOrderMetrics;

    public StartServiceOrderExecutionService(
            ServiceOrderGateway serviceOrderGateway,
            ServiceOrderExecutionGateway serviceOrderExecutionGateway,
            ServiceOrderMetrics serviceOrderMetrics
    ) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.serviceOrderExecutionGateway = serviceOrderExecutionGateway;
        this.serviceOrderMetrics = serviceOrderMetrics;
    }

    @Override
    public ServiceOrder handle(StartServiceOrderExecutionCommand command) {
        long startTime = System.currentTimeMillis();
        try {
            ServiceOrder serviceOrder = serviceOrderGateway.findById(command.id())
                    .orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

            String fromStatus = serviceOrder.getStatus().name();
            serviceOrder.startProgress();
            String toStatus = serviceOrder.getStatus().name();

            serviceOrderGateway.save(serviceOrder);

            ServiceOrderExecution serviceOrderExecution = new ServiceOrderExecution(command.id());
            serviceOrderExecution.start();
            serviceOrderExecutionGateway.save(serviceOrderExecution);

            serviceOrderMetrics.incrementStatusChange(fromStatus, toStatus);
            serviceOrderMetrics.recordProcessingDuration("start_execution", "success", System.currentTimeMillis() - startTime);

            return serviceOrder;
        } catch (Exception e) {
            serviceOrderMetrics.incrementError("start_execution", e.getClass().getSimpleName());
            serviceOrderMetrics.recordProcessingDuration("start_execution", "error", System.currentTimeMillis() - startTime);
            throw e;
        }
    }
}
