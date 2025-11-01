package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.CompleteServiceOrderService;
import com.fiapchallenge.garage.application.serviceorder.command.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompleteServiceOrderServiceTest {

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @InjectMocks
    private CompleteServiceOrderService completeServiceOrderService;

    @Test
    @DisplayName("Deve completar ordem de serviço com sucesso")
    void shouldCompleteServiceOrderSuccessfully() {
        UUID serviceOrderId = UUID.randomUUID();
        ServiceOrder serviceOrder = new ServiceOrder(serviceOrderId, "Test", UUID.randomUUID(), 
                ServiceOrderStatus.IN_PROGRESS, List.of(), List.of());
        
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        CompleteServiceOrderCommand command = new CompleteServiceOrderCommand(serviceOrderId);
        ServiceOrder result = completeServiceOrderService.handle(command);

        assertEquals(ServiceOrderStatus.COMPLETED, result.getStatus());
        verify(serviceOrderRepository).findByIdOrThrow(serviceOrderId);
        verify(serviceOrderRepository).save(serviceOrder);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ordem de serviço não está em status IN_PROGRESS")
    void shouldThrowExceptionWhenNotInProgressStatus() {
        UUID serviceOrderId = UUID.randomUUID();
        ServiceOrder serviceOrder = new ServiceOrder(serviceOrderId, "Test", UUID.randomUUID(), 
                ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of());
        
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);

        CompleteServiceOrderCommand command = new CompleteServiceOrderCommand(serviceOrderId);
        
        assertThrows(IllegalStateException.class, () -> completeServiceOrderService.handle(command));
    }
}