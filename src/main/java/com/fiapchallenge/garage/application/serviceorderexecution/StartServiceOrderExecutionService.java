package com.fiapchallenge.garage.application.serviceorderexecution;

import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartServiceOrderExecutionService implements StartServiceOrderExecutionUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final ServiceOrderExecutionRepository serviceOrderExecutionRepository;

    public StartServiceOrderExecutionService(ServiceOrderGateway serviceOrderGateway, ServiceOrderExecutionRepository serviceOrderExecutionRepository) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.serviceOrderExecutionRepository = serviceOrderExecutionRepository;
    }

    @Override
    public ServiceOrder handle(StartServiceOrderExecutionCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findById(command.id())
                .orElseThrow(() -> new ServiceOrderNotFoundException(command.id()));

        serviceOrder.startProgress();
        serviceOrderGateway.save(serviceOrder);

        ServiceOrderExecution serviceOrderExecution = new ServiceOrderExecution(command.id());
        serviceOrderExecution.start();
        serviceOrderExecutionRepository.save(serviceOrderExecution);

        return serviceOrder;
    }
}
