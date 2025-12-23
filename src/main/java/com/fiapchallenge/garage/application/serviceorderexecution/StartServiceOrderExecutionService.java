package com.fiapchallenge.garage.application.serviceorderexecution;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartServiceOrderExecutionService implements StartServiceOrderExecutionUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final ServiceOrderExecutionRepository serviceOrderExecutionRepository;

    public StartServiceOrderExecutionService(ServiceOrderRepository serviceOrderRepository, ServiceOrderExecutionRepository serviceOrderExecutionRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderExecutionRepository = serviceOrderExecutionRepository;
    }

    @Override
    public ServiceOrder handle(StartServiceOrderExecutionCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada"));

        serviceOrder.startProgress();
        serviceOrderRepository.save(serviceOrder);

        ServiceOrderExecution serviceOrderExecution = new ServiceOrderExecution(command.id());
        serviceOrderExecution.start();
        serviceOrderExecutionRepository.save(serviceOrderExecution);

        return serviceOrder;
    }
}
