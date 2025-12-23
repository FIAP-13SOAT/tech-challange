package com.fiapchallenge.garage.application.serviceorder.track;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderTracking;

import java.util.UUID;

public interface TrackServiceOrderUseCase {
    ServiceOrderTracking handle(UUID serviceOrderId, String cpfCnpj);
}
