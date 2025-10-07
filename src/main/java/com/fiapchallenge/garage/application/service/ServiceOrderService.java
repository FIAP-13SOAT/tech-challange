package com.fiapchallenge.garage.application.service;

import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.ServiceOrderRepositoryImpl;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceOrderService {

    private final ServiceOrderRepositoryImpl serviceOrderRepository;

    public ServiceOrderService(ServiceOrderRepositoryImpl serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    public ServiceOrder create(ServiceOrderRequestDTO serviceOrderRequestDTO) {
        ServiceOrder serviceOrder = new ServiceOrder(serviceOrderRequestDTO);

        return serviceOrderRepository.save(serviceOrder);
    }
}
