package com.fiapchallenge.garage.application.serviceorder.finishdiagnosis;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishServiceOrderDiagnosticService implements FinishServiceOrderDiagnosticUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public FinishServiceOrderDiagnosticService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = this.serviceOrderRepository.findById(command.id()).orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

        serviceOrder.sendToApproval();
        serviceOrderRepository.save(serviceOrder);

        return serviceOrder;
    }
}
