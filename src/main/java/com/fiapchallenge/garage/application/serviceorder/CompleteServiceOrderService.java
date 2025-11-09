package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompleteServiceOrderService implements CompleteServiceOrderUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase;

    public CompleteServiceOrderService(ServiceOrderRepository serviceOrderRepository, FinishServiceOrderExecutionUseCase finishServiceOrderExecutionUseCase) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.finishServiceOrderExecutionUseCase = finishServiceOrderExecutionUseCase;
    }

    @Override
    @Transactional
    public ServiceOrder handle(CompleteServiceOrderCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.serviceOrderId())
            .orElseThrow(() -> new SoatNotFoundException("Ordem de Serviço não encontrada"));

        serviceOrder.complete();

        serviceOrderRepository.save(serviceOrder);

        finishServiceOrderExecutionUseCase.handle(new FinishServiceOrderExecutionCommand(command.serviceOrderId()));

        return serviceOrder;
    }
}
