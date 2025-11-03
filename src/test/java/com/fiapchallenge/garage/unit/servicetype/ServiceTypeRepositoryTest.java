package com.fiapchallenge.garage.unit.servicetype;

import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.ServiceTypeRepositoryImpl;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.shared.mapper.ServiceTypeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTypeRepositoryTest {

    @Mock
    private JpaServiceTypeRepository jpaServiceTypeRepository;
    
    @Mock
    private ServiceTypeMapper serviceTypeMapper;

    @InjectMocks
    private ServiceTypeRepositoryImpl serviceTypeRepository;

    @Test
    @DisplayName("Deve encontrar todos os tipos de serviço")
    void shouldFindAllServiceTypes() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        
        ServiceTypeEntity entity1 = new ServiceTypeEntity();
        entity1.setId(id1);
        entity1.setDescription("Service 1");
        entity1.setValue(BigDecimal.valueOf(100));
        
        ServiceTypeEntity entity2 = new ServiceTypeEntity();
        entity2.setId(id2);
        entity2.setDescription("Service 2");
        entity2.setValue(BigDecimal.valueOf(200));

        ServiceType serviceType1 = new ServiceType(id1, BigDecimal.valueOf(100), "Service 1");
        ServiceType serviceType2 = new ServiceType(id2, BigDecimal.valueOf(200), "Service 2");
        
        when(jpaServiceTypeRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(serviceTypeMapper.toDomain(entity1)).thenReturn(serviceType1);
        when(serviceTypeMapper.toDomain(entity2)).thenReturn(serviceType2);

        List<ServiceType> result = serviceTypeRepository.findAll();

        assertEquals(2, result.size());
        assertEquals("Service 1", result.get(0).getDescription());
        assertEquals("Service 2", result.get(1).getDescription());
        verify(jpaServiceTypeRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem tipos de serviço")
    void shouldReturnEmptyListWhenNoServiceTypesExist() {
        when(jpaServiceTypeRepository.findAll()).thenReturn(List.of());

        List<ServiceType> result = serviceTypeRepository.findAll();

        assertTrue(result.isEmpty());
        verify(jpaServiceTypeRepository).findAll();
    }
}