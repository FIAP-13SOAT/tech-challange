package com.fiapchallenge.garage.presenters.serviceorderexecution;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.presenters.serviceorder.ServiceOrderPresenter;

public class ServiceOrderExecutionPresenter {

    private final ServiceOrderPresenter serviceOrderPresenter;

    public ServiceOrderExecutionPresenter() {
        this.serviceOrderPresenter = new ServiceOrderPresenter();
    }

    public ServiceOrderResponseDTO present(ServiceOrder serviceOrder) {
        return serviceOrderPresenter.present(serviceOrder);
    }
}
