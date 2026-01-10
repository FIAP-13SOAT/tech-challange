package com.fiapchallenge.garage.application.vehicle.delete;

import java.util.UUID;

public interface DeleteVehicleUseCase {
    void handle(UUID id);
}