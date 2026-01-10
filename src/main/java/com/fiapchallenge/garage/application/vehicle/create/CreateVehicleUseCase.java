package com.fiapchallenge.garage.application.vehicle.create;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;

public interface CreateVehicleUseCase {

    Vehicle handle(CreateVehicleCommand command);
}
