package com.fiapchallenge.garage.presenters.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderItemDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

import java.util.List;

public class ServiceOrderPresenter {

    public ServiceOrderResponseDTO present(ServiceOrder serviceOrder) {
        List<ServiceTypeDTO> serviceTypeDTOs = serviceOrder.getServiceTypeList() != null
                ? serviceOrder.getServiceTypeList().stream()
                .map(serviceType -> new ServiceTypeDTO(serviceType.getId(), serviceType.getDescription(), serviceType.getValue()))
                .toList()
                : List.of();

        List<ServiceOrderItemDTO> serviceItemDTOs = serviceOrder.getStockItems() != null
                ? serviceOrder.getStockItems().stream()
                .map(item -> new ServiceOrderItemDTO(item.getId(), item.getStockId(), item.getQuantity()))
                .toList()
                : List.of();

        return new ServiceOrderResponseDTO(
                serviceOrder.getId(),
                serviceOrder.getVehicleId(),
                serviceOrder.getObservations(),
                serviceOrder.getStatus(),
                serviceTypeDTOs,
                serviceItemDTOs
        );
    }

    public List<ServiceOrderResponseDTO> present(List<ServiceOrder> serviceOrders) {
        return serviceOrders.stream().map(this::present).toList();
    }
}
