package com.fiapchallenge.garage.unit.servicetype;

import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.ServiceTypeRepositoryImpl;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.unit.servicetype.utils.factory.ServiceTypeTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTypeUnitTest {

    @Mock
    private ServiceTypeRepositoryImpl serviceOrderRepository;

    @InjectMocks
    private CreateServiceTypeService createServiceTypeService;

    @Test
    @DisplayName("Criar ordem de serviço")
    void shouldCreateServiceOrder() {
        when(serviceOrderRepository.save(any(ServiceType.class))).thenReturn(ServiceTypeTestFactory.build());

        ServiceType serviceType = createServiceTypeService.handle(ServiceTypeTestFactory.buildCommand());

        assertNotNull(serviceType);
        assertEquals(ServiceTypeTestFactory.ID, serviceType.getId());
        assertEquals(ServiceTypeTestFactory.VALUE, serviceType.getValue());
        assertEquals(ServiceTypeTestFactory.DESCRIPTION, serviceType.getDescription());
    }
}
