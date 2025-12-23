package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderItemDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.mapper.ServiceTypeMapper;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

import java.util.List;

public class ServiceOrderMapper {

    public static ServiceOrderResponseDTO toResponseDTO(ServiceOrder serviceOrder) {
        List<ServiceTypeDTO> serviceTypeDTOs = serviceOrder.getServiceTypeList() != null
                ? serviceOrder.getServiceTypeList().stream()
                    .map(ServiceTypeMapper::toResponseDTO)
                    .toList()
                : List.of();

        List<ServiceOrderItemDTO> serviceItemDTOs = serviceOrder.getStockItems() != null
                ? serviceOrder.getStockItems().stream()
                    .map(ServiceOrderItemDTO::fromDomain)
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
}