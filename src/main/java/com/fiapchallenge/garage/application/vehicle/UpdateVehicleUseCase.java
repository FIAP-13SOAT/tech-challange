package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.application.vehicle.command.UpdateVehicleCommand;

public interface UpdateVehicleUseCase {
    Vehicle handle(UpdateVehicleCommand command);
}
