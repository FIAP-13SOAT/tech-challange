package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.AddServiceTypesCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AddServiceTypesService implements AddServiceTypesUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public AddServiceTypesService(ServiceOrderRepository serviceOrderRepository,
                                  ServiceTypeRepository serviceTypeRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceOrder handle(AddServiceTypesCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(command.serviceOrderId());
        List<ServiceType> serviceTypes = command.serviceTypeIds().stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList();
        serviceOrder.addServiceTypes(serviceTypes);
        return serviceOrderRepository.save(serviceOrder);
    }
}