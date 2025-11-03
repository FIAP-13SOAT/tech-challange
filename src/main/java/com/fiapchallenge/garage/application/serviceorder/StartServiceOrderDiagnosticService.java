package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.shared.service.BaseServiceOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartServiceOrderDiagnosticService extends BaseServiceOrderService implements StartServiceOrderDiagnosticUseCase {

    public StartServiceOrderDiagnosticService(ServiceOrderRepository serviceOrderRepository) {
        super(serviceOrderRepository);
    }

    @Override
    public ServiceOrder handle(StartServiceOrderDiagnosticCommand command) {
        return executeServiceOrderOperation(command.id(), ServiceOrder::startDiagnostic);
    }
}
