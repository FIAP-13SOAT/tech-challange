package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartServiceOrderDiagnosticService implements StartServiceOrderDiagnosticUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public StartServiceOrderDiagnosticService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder handle(StartServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço não encontrada"));

        serviceOrder.startDiagnostic();
        serviceOrderRepository.save(serviceOrder);

        return serviceOrder;
    }
}
