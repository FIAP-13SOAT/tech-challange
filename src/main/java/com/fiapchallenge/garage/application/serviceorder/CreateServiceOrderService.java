package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CreateServiceOrderService implements CreateServiceOrderUseCase {

    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceOrderRepository serviceOrderRepository;

    public CreateServiceOrderService(ServiceTypeRepository serviceTypeRepository, ServiceOrderRepository serviceOrderRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder handle(CreateServiceOrderCommand command) {
        List<ServiceType> serviceTypesList = command.serviceTypeIdList()
                .stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList();

        ServiceOrder serviceOrder = new ServiceOrder(command);
        serviceOrder.setServiceTypeList(serviceTypesList);
        serviceOrderRepository.save(serviceOrder);

        return serviceOrder;
    }
}
