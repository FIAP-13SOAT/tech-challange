package com.fiapchallenge.garage.shared.service;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class BaseServiceOrderService {
    
    protected final ServiceOrderRepository serviceOrderRepository;
    
    protected BaseServiceOrderService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }
    
    protected ServiceOrder executeServiceOrderOperation(UUID serviceOrderId, Consumer<ServiceOrder> operation) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(serviceOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço não encontrada"));
        
        operation.accept(serviceOrder);
        serviceOrderRepository.save(serviceOrder);
        
        return serviceOrder;
    }
    
    protected ServiceOrder executeServiceOrderOperationWithDirectFind(UUID serviceOrderId, Consumer<ServiceOrder> operation) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);
        operation.accept(serviceOrder);
        return serviceOrderRepository.save(serviceOrder);
    }
}