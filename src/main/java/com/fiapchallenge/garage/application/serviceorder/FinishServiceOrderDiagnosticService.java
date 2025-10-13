package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FinishServiceOrderDiagnosticService implements FinishServiceOrderDiagnosticUseCase {

    private final ServiceOrderRepository serviceOrderRepository;

    public FinishServiceOrderDiagnosticService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public void handle(FinishServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço não encontrada"));

        serviceOrder.sendToApproval();
        serviceOrderRepository.save(serviceOrder);

        // TODO: Criar orçamento automaticamente
    }
}
