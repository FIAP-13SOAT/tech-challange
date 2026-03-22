package com.fiapchallenge.garage.application.serviceorder.deliver;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.shared.metrics.ServiceOrderMetrics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeliverServiceOrderService implements DeliverServiceOrderUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final ServiceOrderMetrics serviceOrderMetrics;

    public DeliverServiceOrderService(ServiceOrderGateway serviceOrderGateway,
                                      ServiceOrderMetrics serviceOrderMetrics) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.serviceOrderMetrics = serviceOrderMetrics;
    }

    @Override
    public ServiceOrder handle(DeliverServiceOrderCommand command) {
        long startTime = System.currentTimeMillis();
        try {
            ServiceOrder serviceOrder = this.serviceOrderGateway.findById(command.serviceOrderId())
                    .orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

            String fromStatus = serviceOrder.getStatus().name();
            serviceOrder.deliver();
            String toStatus = serviceOrder.getStatus().name();

            ServiceOrder saved = this.serviceOrderGateway.save(serviceOrder);

            serviceOrderMetrics.incrementStatusChange(fromStatus, toStatus);
            serviceOrderMetrics.recordProcessingDuration("deliver", "success", System.currentTimeMillis() - startTime);

            return saved;
        } catch (Exception e) {
            serviceOrderMetrics.incrementError("deliver", e.getClass().getSimpleName());
            serviceOrderMetrics.recordProcessingDuration("deliver", "error", System.currentTimeMillis() - startTime);
            throw e;
        }
    }
}
