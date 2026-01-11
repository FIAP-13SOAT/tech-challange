package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.deliver.DeliverServiceOrderService;
import com.fiapchallenge.garage.application.serviceorder.deliver.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.serviceorder.exceptions.InvalidStatusToDeliverException;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliverServiceOrderServiceTest {

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @InjectMocks
    private DeliverServiceOrderService deliverServiceOrderService;

    @Test
    @DisplayName("Deve entregar ordem de serviço com sucesso")
    void shouldDeliverServiceOrderSuccessfully() {
        UUID serviceOrderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Test Customer", "test@test.com", "12345678901", new CpfCnpj("667.713.590-00"));
        ServiceOrder serviceOrder = new ServiceOrder(serviceOrderId, "Test", UUID.randomUUID(),
                ServiceOrderStatus.COMPLETED, List.of(), List.of(), customer);

        when(serviceOrderRepository.findById(serviceOrderId)).thenReturn(Optional.of(serviceOrder));
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        DeliverServiceOrderCommand command = new DeliverServiceOrderCommand(serviceOrderId);
        ServiceOrder result = deliverServiceOrderService.handle(command);

        assertEquals(ServiceOrderStatus.DELIVERED, result.getStatus());
        verify(serviceOrderRepository).findById(serviceOrderId);
        verify(serviceOrderRepository).save(serviceOrder);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ordem de serviço não está em status COMPLETED")
    void shouldThrowExceptionWhenNotCompletedStatus() {
        UUID serviceOrderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Test Customer", "test@test.com", "12345678901", new CpfCnpj("667.713.590-00"));
        ServiceOrder serviceOrder = new ServiceOrder(serviceOrderId, "Test", UUID.randomUUID(),
                ServiceOrderStatus.IN_PROGRESS, List.of(), List.of(), customer);

        when(serviceOrderRepository.findById(serviceOrderId)).thenReturn(Optional.of(serviceOrder));

        DeliverServiceOrderCommand command = new DeliverServiceOrderCommand(serviceOrderId);

        assertThrows(InvalidStatusToDeliverException.class, () -> deliverServiceOrderService.handle(command));
    }
}
