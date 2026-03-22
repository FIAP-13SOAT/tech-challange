package com.fiapchallenge.garage.application.serviceorder.finishdiagnosis;

import com.fiapchallenge.garage.application.quote.GenerateQuoteUseCase;
import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.shared.metrics.ServiceOrderMetrics;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishServiceOrderDiagnosticService implements FinishServiceOrderDiagnosticUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final GenerateQuoteUseCase generateQuoteUseCase;
    private final ServiceOrderMetrics serviceOrderMetrics;

    public FinishServiceOrderDiagnosticService(ServiceOrderGateway serviceOrderGateway,
                                               GenerateQuoteUseCase generateQuoteUseCase,
                                               ServiceOrderMetrics serviceOrderMetrics) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.generateQuoteUseCase = generateQuoteUseCase;
        this.serviceOrderMetrics = serviceOrderMetrics;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderDiagnosticCommand command) {
        long startTime = System.currentTimeMillis();
        try {
            ServiceOrder serviceOrder = this.serviceOrderGateway.findById(command.id())
                    .orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

            String fromStatus = serviceOrder.getStatus().name();
            serviceOrder.sendToApproval();
            String toStatus = serviceOrder.getStatus().name();

            serviceOrderGateway.save(serviceOrder);
            generateQuoteUseCase.handle(serviceOrder.getId());

            serviceOrderMetrics.incrementStatusChange(fromStatus, toStatus);
            serviceOrderMetrics.recordProcessingDuration("finish_diagnosis", "success", System.currentTimeMillis() - startTime);

            return serviceOrder;
        } catch (Exception e) {
            serviceOrderMetrics.incrementError("finish_diagnosis", e.getClass().getSimpleName());
            serviceOrderMetrics.recordProcessingDuration("finish_diagnosis", "error", System.currentTimeMillis() - startTime);
            throw e;
        }
    }
}
