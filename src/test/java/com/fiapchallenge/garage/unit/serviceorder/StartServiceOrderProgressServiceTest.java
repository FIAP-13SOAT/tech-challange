package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderProgressService;
import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderProgressCommand;
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
class StartServiceOrderProgressServiceTest {

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @InjectMocks
    private StartServiceOrderProgressService startServiceOrderProgressService;

    @Test
    @DisplayName("Deve iniciar progresso da ordem de serviço com sucesso")
    void shouldStartServiceOrderProgressSuccessfully() {
        UUID serviceOrderId = UUID.randomUUID();
        ServiceOrder serviceOrder = new ServiceOrder(serviceOrderId, "Test", UUID.randomUUID(), 
                ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of());
        
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        StartServiceOrderProgressCommand command = new StartServiceOrderProgressCommand(serviceOrderId);
        ServiceOrder result = startServiceOrderProgressService.handle(command);

        assertEquals(ServiceOrderStatus.IN_PROGRESS, result.getStatus());
        verify(serviceOrderRepository).findByIdOrThrow(serviceOrderId);
        verify(serviceOrderRepository).save(serviceOrder);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ordem de serviço não está em status AWAITING_APPROVAL")
    void shouldThrowExceptionWhenNotInAwaitingApprovalStatus() {
        UUID serviceOrderId = UUID.randomUUID();
        ServiceOrder serviceOrder = new ServiceOrder(serviceOrderId, "Test", UUID.randomUUID(), 
                ServiceOrderStatus.CREATED, List.of(), List.of());
        
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);

        StartServiceOrderProgressCommand command = new StartServiceOrderProgressCommand(serviceOrderId);
        
        assertThrows(IllegalStateException.class, () -> startServiceOrderProgressService.handle(command));
    }
}