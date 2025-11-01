package com.fiapchallenge.garage.application.vehicle;

import java.util.UUID;

public interface DeleteVehicleUseCase {
    void handle(UUID id);
}