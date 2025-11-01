package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.ServiceOrderRepositoryImpl;
import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceOrderRepositoryTest {

    @Mock
    private JpaServiceOrderRepository jpaServiceOrderRepository;

    @Mock
    private JpaServiceTypeRepository jpaServiceTypeRepository;

    @InjectMocks
    private ServiceOrderRepositoryImpl serviceOrderRepository;

    @Test
    @DisplayName("Deve encontrar ordem de serviço por ID")
    void shouldFindServiceOrderById() {
        UUID serviceOrderId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        
        ServiceOrderEntity entity = new ServiceOrderEntity();
        entity.setId(serviceOrderId);
        entity.setObservations("Test");
        entity.setVehicleId(vehicleId);
        entity.setStatus(ServiceOrderStatus.CREATED);
        entity.setServiceTypeList(List.of());
        entity.setStockItems(List.of());

        when(jpaServiceOrderRepository.findById(serviceOrderId)).thenReturn(Optional.of(entity));

        ServiceOrder result = serviceOrderRepository.findByIdOrThrow(serviceOrderId);

        assertEquals(serviceOrderId, result.getId());
        assertEquals("Test", result.getObservations());
        assertEquals(vehicleId, result.getVehicleId());
        assertEquals(ServiceOrderStatus.CREATED, result.getStatus());
        verify(jpaServiceOrderRepository).findById(serviceOrderId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ordem de serviço não for encontrada")
    void shouldThrowExceptionWhenServiceOrderNotFound() {
        UUID serviceOrderId = UUID.randomUUID();
        
        when(jpaServiceOrderRepository.findById(serviceOrderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> serviceOrderRepository.findByIdOrThrow(serviceOrderId));
        verify(jpaServiceOrderRepository).findById(serviceOrderId);
    }
}