package com.fiapchallenge.garage.application.serviceorder.cancel;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.stock.add.AddStockUseCase;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.application.stock.command.AddStockCommand;
import com.fiapchallenge.garage.shared.metrics.ServiceOrderMetrics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelServiceOrderService implements CancelServiceOrderUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final AddStockUseCase addStockUseCase;
    private final ServiceOrderMetrics serviceOrderMetrics;

    public CancelServiceOrderService(ServiceOrderGateway serviceOrderGateway,
                                     AddStockUseCase addStockUseCase,
                                     ServiceOrderMetrics serviceOrderMetrics) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.addStockUseCase = addStockUseCase;
        this.serviceOrderMetrics = serviceOrderMetrics;
    }

    @Override
    public ServiceOrder handle(CancelServiceOrderCommand command) {
        long startTime = System.currentTimeMillis();
        try {
            ServiceOrder serviceOrder = serviceOrderGateway.findById(command.serviceOrderId())
                    .orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

            if (serviceOrder.getStockItems() != null && !serviceOrder.getStockItems().isEmpty()) {
                for (var item : serviceOrder.getStockItems()) {
                    AddStockCommand addCommand = new AddStockCommand(item.getStockId(), item.getQuantity());
                    addStockUseCase.handle(addCommand);
                }
            }

            String fromStatus = serviceOrder.getStatus().name();
            serviceOrder.cancel();
            String toStatus = serviceOrder.getStatus().name();

            ServiceOrder saved = serviceOrderGateway.save(serviceOrder);

            serviceOrderMetrics.incrementStatusChange(fromStatus, toStatus);
            serviceOrderMetrics.recordProcessingDuration("cancel", "success", System.currentTimeMillis() - startTime);

            return saved;
        } catch (Exception e) {
            serviceOrderMetrics.incrementError("cancel", e.getClass().getSimpleName());
            serviceOrderMetrics.recordProcessingDuration("cancel", "error", System.currentTimeMillis() - startTime);
            throw e;
        }
    }
}
