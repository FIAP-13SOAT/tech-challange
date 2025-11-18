package com.fiapchallenge.garage.unit.serviceorder.util.factory;

import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.unit.servicetype.utils.factory.ServiceTypeTestFactory;

import java.util.List;
import java.util.UUID;

public class ServiceOrderTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final ServiceOrderStatus STATUS = ServiceOrderStatus.RECEIVED;
    public static final List<ServiceType> SERVICE_TYPE_LIST = List.of(ServiceTypeTestFactory.build());
    public static final String OBSERVATIONS = "Troca de óleo e filtro";

    public static CreateServiceOrderCommand createServiceOrderCommand(UUID vehicleId, UUID customerId) {
        return new CreateServiceOrderCommand(
                OBSERVATIONS,
                vehicleId,
                customerId,
                getServiceTypeListIds(),
                List.of()
        );
    }

    public static ServiceOrder createServiceOrder(UUID vehicleId, UUID customerId) {
        return new ServiceOrder(
                ID,
                OBSERVATIONS,
                vehicleId,
                customerId,
                STATUS,
                SERVICE_TYPE_LIST,
                List.of()
        );
    }

    public static ServiceOrder createServiceOrder(UUID vehicleId, UUID customerId, ServiceOrderStatus status) {
        return new ServiceOrder(
                ID,
                OBSERVATIONS,
                vehicleId,
                customerId,
                status,
                SERVICE_TYPE_LIST,
                List.of()
        );
    }

    public static List<UUID> getServiceTypeListIds() {
        return SERVICE_TYPE_LIST.stream().map(ServiceType::getId).toList();
    }
}
