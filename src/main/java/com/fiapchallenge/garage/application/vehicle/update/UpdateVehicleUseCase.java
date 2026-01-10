package com.fiapchallenge.garage.application.vehicle.update;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;

public interface UpdateVehicleUseCase {
    Vehicle handle(UpdateVehicleCommand command);
}
