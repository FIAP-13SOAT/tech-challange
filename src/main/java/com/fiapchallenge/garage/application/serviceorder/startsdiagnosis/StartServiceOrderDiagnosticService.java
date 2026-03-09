package com.fiapchallenge.garage.application.serviceorder.startsdiagnosis;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartServiceOrderDiagnosticService implements StartServiceOrderDiagnosticUseCase {

    private final ServiceOrderGateway serviceOrderGateway;

    public StartServiceOrderDiagnosticService(ServiceOrderGateway serviceOrderGateway) {
        this.serviceOrderGateway = serviceOrderGateway;
    }

    @Override
    public ServiceOrder handle(StartServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findById(command.id()).orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

        serviceOrder.startDiagnostic();
        serviceOrderGateway.save(serviceOrder);

        return serviceOrder;
    }
}
