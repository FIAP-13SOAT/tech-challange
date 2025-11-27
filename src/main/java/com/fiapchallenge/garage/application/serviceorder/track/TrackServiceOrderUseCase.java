package com.fiapchallenge.garage.application.serviceorder.track;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderTrackingDTO;

import java.util.UUID;

public interface TrackServiceOrderUseCase {
    ServiceOrderTrackingDTO handle(UUID serviceOrderId, String cpfCnpj);
}
