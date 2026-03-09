package com.fiapchallenge.garage.controllers.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderTrackingDTO;
import com.fiapchallenge.garage.application.serviceorder.track.TrackServiceOrderUseCase;

import java.util.UUID;

public class PublicServiceOrderController {

    private final TrackServiceOrderUseCase trackServiceOrderUseCase;

    public PublicServiceOrderController(TrackServiceOrderUseCase trackServiceOrderUseCase) {
        this.trackServiceOrderUseCase = trackServiceOrderUseCase;
    }

    public ServiceOrderTrackingDTO trackServiceOrder(UUID serviceOrderId, String cpfCnpj) {
        return trackServiceOrderUseCase.handle(serviceOrderId, cpfCnpj);
    }
}
