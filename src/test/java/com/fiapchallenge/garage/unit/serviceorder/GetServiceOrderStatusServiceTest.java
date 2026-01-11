package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderStatusDTO;
import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.application.serviceorder.getstatus.GetServiceOrderStatusCommand;
import com.fiapchallenge.garage.application.serviceorder.getstatus.GetServiceOrderStatusService;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetServiceOrderStatusServiceTest {

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @Mock
    private MessageSource messageSource;

    @Mock
    private ServiceOrder serviceOrder;

    @InjectMocks
    private GetServiceOrderStatusService getServiceOrderStatusService;

    @Test
    void shouldReturnServiceOrderStatusWithTranslatedMessage() {
        UUID serviceOrderId = UUID.randomUUID();
        GetServiceOrderStatusCommand command = new GetServiceOrderStatusCommand(serviceOrderId);

        when(serviceOrderRepository.findById(serviceOrderId)).thenReturn(Optional.of(serviceOrder));
        when(serviceOrder.getId()).thenReturn(serviceOrderId);
        when(serviceOrder.getStatus()).thenReturn(ServiceOrderStatus.IN_DIAGNOSIS);
        when(messageSource.getMessage(eq("serviceorder.status.IN_DIAGNOSIS"), eq(null), any(Locale.class)))
                .thenReturn("Em Diagnóstico");

        ServiceOrderStatusDTO result = getServiceOrderStatusService.handle(command);

        assertEquals(serviceOrderId, result.id());
        assertEquals(ServiceOrderStatus.IN_DIAGNOSIS, result.status());
        assertEquals("Em Diagnóstico", result.statusMessage());
    }

    @Test
    void shouldThrowExceptionWhenServiceOrderNotFound() {
        UUID serviceOrderId = UUID.randomUUID();
        GetServiceOrderStatusCommand command = new GetServiceOrderStatusCommand(serviceOrderId);

        when(serviceOrderRepository.findById(serviceOrderId)).thenReturn(Optional.empty());

        assertThrows(ServiceOrderNotFoundException.class, () -> getServiceOrderStatusService.handle(command));
    }
}