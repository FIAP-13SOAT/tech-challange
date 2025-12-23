package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderService;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderCommand;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.util.List;
import java.util.UUID;

public class ServiceOrderFixture {

    public static ServiceOrder createServiceOrder(UUID vehicleId, UUID customerId, CreateServiceOrderService createServiceOrderService, CreateServiceTypeService createServiceTypeService, ServiceOrderRepository serviceOrderRepository) {
        ServiceType serviceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        CreateServiceOrderCommand command = new CreateServiceOrderCommand(
            "Test service order",
            vehicleId,
            customerId,
            List.of(serviceType.getId()),
            List.of()
        );

        ServiceOrder serviceOrder = createServiceOrderService.handle(command);
        return serviceOrderRepository.save(serviceOrder);
    }

    public static ServiceOrder createServiceOrder(Customer customer, UUID vehicleId, ServiceOrderStatus status, CreateServiceTypeService createServiceTypeService, ServiceOrderRepository serviceOrderRepository) {
        ServiceType serviceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        ServiceOrder serviceOrder = new ServiceOrder(null, "Test service order", vehicleId, status, List.of(serviceType), List.of(), customer);
        return serviceOrderRepository.save(serviceOrder);
    }
}
