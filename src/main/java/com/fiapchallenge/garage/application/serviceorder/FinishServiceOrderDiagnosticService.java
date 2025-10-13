package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.quote.CreateQuoteUseCase;
import com.fiapchallenge.garage.application.quote.command.CreateQuoteCommand;
import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FinishServiceOrderDiagnosticService implements FinishServiceOrderDiagnosticUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final CreateQuoteUseCase createQuoteUseCase;

    public FinishServiceOrderDiagnosticService(ServiceOrderRepository serviceOrderRepository, CreateQuoteUseCase createQuoteUseCase) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.createQuoteUseCase = createQuoteUseCase;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderDiagnosticCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço não encontrada"));

        serviceOrder.sendToApproval();
        serviceOrderRepository.save(serviceOrder);

        createQuoteUseCase.handle(new CreateQuoteCommand(serviceOrder));

        return serviceOrder;
    }
}
