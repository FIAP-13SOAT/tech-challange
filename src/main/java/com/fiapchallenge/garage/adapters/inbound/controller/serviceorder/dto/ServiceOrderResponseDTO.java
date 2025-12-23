package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;

import java.util.List;
import java.util.UUID;

public record ServiceOrderResponseDTO(
        UUID id,
        UUID vehicleId,
        String observations,
        ServiceOrderStatus status,
        List<ServiceTypeDTO> serviceTypeList,
        List<ServiceOrderItemDTO> serviceItems
) {}