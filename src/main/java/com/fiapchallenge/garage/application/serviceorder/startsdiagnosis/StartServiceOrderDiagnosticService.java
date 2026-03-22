package com.fiapchallenge.garage.application.serviceorder.startsdiagnosis;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.shared.metrics.ServiceOrderMetrics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartServiceOrderDiagnosticService implements StartServiceOrderDiagnosticUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final ServiceOrderMetrics serviceOrderMetrics;

    public StartServiceOrderDiagnosticService(ServiceOrderGateway serviceOrderGateway,
                                              ServiceOrderMetrics serviceOrderMetrics) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.serviceOrderMetrics = serviceOrderMetrics;
    }

    @Override
    public ServiceOrder handle(StartServiceOrderDiagnosticCommand command) {
        long startTime = System.currentTimeMillis();
        try {
            ServiceOrder serviceOrder = serviceOrderGateway.findById(command.id())
                    .orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

            String fromStatus = serviceOrder.getStatus().name();
            serviceOrder.startDiagnostic();
            String toStatus = serviceOrder.getStatus().name();

            serviceOrderGateway.save(serviceOrder);

            serviceOrderMetrics.incrementStatusChange(fromStatus, toStatus);
            serviceOrderMetrics.recordProcessingDuration("start_diagnosis", "success", System.currentTimeMillis() - startTime);

            return serviceOrder;
        } catch (Exception e) {
            serviceOrderMetrics.incrementError("start_diagnosis", e.getClass().getSimpleName());
            serviceOrderMetrics.recordProcessingDuration("start_diagnosis", "error", System.currentTimeMillis() - startTime);
            throw e;
        }
    }
}
