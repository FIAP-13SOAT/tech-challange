package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.shared.service.BaseServiceOrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FinishServiceOrderDiagnosticService extends BaseServiceOrderService implements FinishServiceOrderDiagnosticUseCase {

    public FinishServiceOrderDiagnosticService(ServiceOrderRepository serviceOrderRepository) {
        super(serviceOrderRepository);
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = executeServiceOrderOperation(command.id(), ServiceOrder::sendToApproval);
        return serviceOrder;
    }
}
