package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderTrackingDTO;

import java.util.UUID;

public interface TrackServiceOrderUseCase {
    ServiceOrderTrackingDTO handle(UUID serviceOrderId, String cpfCnpj);
}