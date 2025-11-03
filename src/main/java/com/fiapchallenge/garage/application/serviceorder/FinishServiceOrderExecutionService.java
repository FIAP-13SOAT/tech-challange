package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
import com.fiapchallenge.garage.shared.service.BaseServiceOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishServiceOrderExecutionService extends BaseServiceOrderService implements FinishServiceOrderExecutionUseCase {

    private final ServiceOrderExecutionRepository serviceOrderExecutionRepository;

    public FinishServiceOrderExecutionService(ServiceOrderRepository serviceOrderRepository, ServiceOrderExecutionRepository serviceOrderExecutionRepository) {
        super(serviceOrderRepository);
        this.serviceOrderExecutionRepository = serviceOrderExecutionRepository;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderExecutionCommand command) {
        ServiceOrder serviceOrder = executeServiceOrderOperation(command.id(), ServiceOrder::complete);
        
        ServiceOrderExecution serviceOrderExecution = serviceOrderExecutionRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Execução da ordem de serviço não encontrada"));
        serviceOrderExecution.finish();
        serviceOrderExecutionRepository.save(serviceOrderExecution);
        
        return serviceOrder;
    }
}
