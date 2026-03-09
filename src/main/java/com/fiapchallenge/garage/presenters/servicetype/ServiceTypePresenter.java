package com.fiapchallenge.garage.presenters.servicetype;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.util.List;

public class ServiceTypePresenter {

    public ServiceTypeDTO present(ServiceType serviceType) {
        return new ServiceTypeDTO(
                serviceType.getId(),
                serviceType.getDescription(),
                serviceType.getValue()
        );
    }

    public List<ServiceTypeDTO> present(List<ServiceType> serviceTypes) {
        return serviceTypes.stream().map(this::present).toList();
    }
}
