package com.fiapchallenge.garage.application.serviceorderexecution;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.serviceorderexecution.exceptions.ServiceOrderExecutionNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishServiceOrderExecutionService implements FinishServiceOrderExecutionUseCase {

    private final ServiceOrderExecutionRepository serviceOrderExecutionRepository;
    private final ServiceOrderRepository serviceOrderRepository;

    public FinishServiceOrderExecutionService(ServiceOrderRepository serviceOrderRepository, ServiceOrderExecutionRepository serviceOrderExecutionRepository) {
        this.serviceOrderExecutionRepository = serviceOrderExecutionRepository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder handle(FinishServiceOrderExecutionCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(command.id())
                .orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

        ServiceOrderExecution serviceOrderExecution = serviceOrderExecutionRepository.findById(command.id())
                .orElseThrow(() -> new ServiceOrderExecutionNotFoundException(command.id()));

        serviceOrderExecution.finish();
        serviceOrderExecutionRepository.save(serviceOrderExecution);

        return serviceOrder;
    }
}
