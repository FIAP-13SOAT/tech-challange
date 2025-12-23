package com.fiapchallenge.garage.adapters.inbound.controller.servicetype.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.util.List;

public class ServiceTypeMapper {

    public static ServiceTypeDTO toResponseDTO(ServiceType serviceType) {
        return new ServiceTypeDTO(
                serviceType.getId(),
                serviceType.getDescription(),
                serviceType.getValue()
        );
    }

    public static List<ServiceTypeDTO> toResponseDTOList(List<ServiceType> serviceTypes) {
        return serviceTypes.stream()
                .map(ServiceTypeMapper::toResponseDTO)
                .toList();
    }
}