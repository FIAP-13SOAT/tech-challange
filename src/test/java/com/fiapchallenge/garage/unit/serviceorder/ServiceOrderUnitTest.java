package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.CreateServiceOrderService;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import com.fiapchallenge.garage.unit.serviceorder.util.factory.ServiceOrderTestFactory;
import com.fiapchallenge.garage.unit.servicetype.utils.factory.ServiceTypeTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceOrderUnitTest {

    @Mock
    ServiceTypeRepository serviceTypeRepository;

    @Mock
    ServiceOrderRepository serviceOrderRepository;

    @InjectMocks
    private CreateServiceOrderService createServiceOrderService;

    @Test
    @DisplayName("Criação de Ordem de Serviço")
    void shouldCreateServiceOrder() {
        // Arrange
        ServiceType serviceType = ServiceTypeTestFactory.build();
        UUID vehicleId = UUID.randomUUID();

        when(serviceTypeRepository.findByIdOrThrow(any(UUID.class))).thenReturn(serviceType);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(ServiceOrderTestFactory.createServiceOrder(vehicleId));

        // Act
        ServiceOrder serviceOrder = createServiceOrderService.handle(ServiceOrderTestFactory.createServiceOrderCommand(vehicleId));

        // Assert
        assertEquals(ServiceOrderTestFactory.OBSERVATIONS, serviceOrder.getObservations());
        assertEquals(ServiceOrderStatus.CREATED, serviceOrder.getStatus());
        assertEquals(vehicleId, serviceOrder.getVehicleId());
        assertEquals(ServiceOrderTestFactory.SERVICE_TYPE_LIST.size(), serviceOrder.getServiceTypeList().size());
        assertEquals(ServiceOrderTestFactory.getServiceTypeListIds(), serviceOrder.getServiceTypeListIds());
    }
}
