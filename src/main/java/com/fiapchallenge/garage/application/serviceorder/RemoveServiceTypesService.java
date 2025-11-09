package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.RemoveServiceTypesCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RemoveServiceTypesService implements RemoveServiceTypesUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public RemoveServiceTypesService(ServiceOrderRepository serviceOrderRepository,
                                     ServiceTypeRepository serviceTypeRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceOrder handle(RemoveServiceTypesCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(command.serviceOrderId());
        List<ServiceType> serviceTypes = command.serviceTypeIds().stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList();
        serviceOrder.removeServiceTypes(serviceTypes);
        return serviceOrderRepository.save(serviceOrder);
    }
}