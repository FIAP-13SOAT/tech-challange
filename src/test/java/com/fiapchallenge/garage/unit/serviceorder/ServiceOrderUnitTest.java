package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.finishdiagnosis.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.startsdiagnosis.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderService;
import com.fiapchallenge.garage.application.serviceorder.finishdiagnosis.FinishServiceOrderDiagnosticService;
import com.fiapchallenge.garage.application.serviceorder.startsdiagnosis.StartServiceOrderDiagnosticService;
import com.fiapchallenge.garage.application.serviceorderexecution.FinishServiceOrderExecutionService;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionService;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceOrderUnitTest {

    @Mock
    ServiceTypeRepository serviceTypeRepository;

    @Mock
    ServiceOrderRepository serviceOrderRepository;

    @Mock
    ServiceOrderExecutionRepository serviceOrderExecutionRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    private CreateServiceOrderService createServiceOrderService;

    @InjectMocks
    private StartServiceOrderDiagnosticService startServiceOrderDiagnosticService;

    @InjectMocks
    private FinishServiceOrderDiagnosticService finishServiceOrderDiagnosticService;

    @InjectMocks
    private StartServiceOrderExecutionService startServiceOrderService;

    @InjectMocks
    private FinishServiceOrderExecutionService finishServiceOrderExecutionService;

    private Customer customer = new Customer(UUID.randomUUID(), "Test Customer", "test@test.com", "12345678901", new CpfCnpj("667.713.590-00"));;

    @Test
    @DisplayName("Criação de Ordem de Serviço")
    void shouldCreateServiceOrder() {
        ServiceType serviceType = ServiceTypeTestFactory.build();
        UUID vehicleId = UUID.randomUUID();

        when(serviceTypeRepository.findByIdOrThrow(any(UUID.class))).thenReturn(serviceType);
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(ServiceOrderTestFactory.createServiceOrder(vehicleId, this.customer));

        ServiceOrder serviceOrder = createServiceOrderService.handle(ServiceOrderTestFactory.createServiceOrderCommand(vehicleId, customer.getId()));

        assertEquals(ServiceOrderTestFactory.OBSERVATIONS, serviceOrder.getObservations());
        assertEquals(ServiceOrderStatus.RECEIVED, serviceOrder.getStatus());
        assertEquals(vehicleId, serviceOrder.getVehicleId());
        assertEquals(ServiceOrderTestFactory.SERVICE_TYPE_LIST.size(), serviceOrder.getServiceTypeList().size());
        assertEquals(ServiceOrderTestFactory.getServiceTypeListIds(), serviceOrder.getServiceTypeListIds());
    }

    @Test
    @DisplayName("Iniciar diagnóstico")
    void shouldStartDiagnostic() {
        UUID vehicleId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Optional<ServiceOrder> mockedServiceOrder = Optional.of(ServiceOrderTestFactory.createServiceOrder(vehicleId, customer));
        when(serviceOrderRepository.findById(any(UUID.class))).thenReturn(mockedServiceOrder);

        ServiceOrder serviceOrder = startServiceOrderDiagnosticService.handle(new StartServiceOrderDiagnosticCommand(ServiceOrderTestFactory.ID));

        assertEquals(ServiceOrderStatus.IN_DIAGNOSIS, serviceOrder.getStatus());
    }

    @Test
    @DisplayName("Finalizar diagnóstico")
    void shouldFinishDiagnostic() {
        UUID vehicleId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Optional<ServiceOrder> mockedServiceOrder = Optional.of(ServiceOrderTestFactory.createServiceOrder(vehicleId, customer, ServiceOrderStatus.IN_DIAGNOSIS));
        when(serviceOrderRepository.findById(any(UUID.class))).thenReturn(mockedServiceOrder);
        ServiceOrder serviceOrder = finishServiceOrderDiagnosticService.handle(new FinishServiceOrderDiagnosticCommand(ServiceOrderTestFactory.ID));

        assertEquals(ServiceOrderStatus.AWAITING_APPROVAL, serviceOrder.getStatus());
        verify(serviceOrderRepository).save(serviceOrder);
    }
}
