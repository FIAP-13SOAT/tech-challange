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
    public static final ServiceOrderStatus STATUS = ServiceOrderStatus.CREATED;
    public static final List<ServiceType> SERVICE_TYPE_LIST = List.of(ServiceTypeTestFactory.build());
    public static final String OBSERVATIONS = "Troca de óleo e filtro";

    public static CreateServiceOrderCommand createServiceOrderCommand(UUID vehicleId) {
        return new CreateServiceOrderCommand(
                OBSERVATIONS,
                vehicleId,
                getServiceTypeListIds()
        );
    }

    public static ServiceOrder createServiceOrder(UUID vehicleId) {
        return new ServiceOrder(
                ID,
                OBSERVATIONS,
                vehicleId,
                STATUS,
                SERVICE_TYPE_LIST
        );
    }

    public static List<UUID> getServiceTypeListIds() {
        return SERVICE_TYPE_LIST.stream().map(ServiceType::getId).toList();
    }
}
