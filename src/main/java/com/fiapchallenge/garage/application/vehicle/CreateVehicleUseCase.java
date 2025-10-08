package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.command.CreateVehicleCommand;

public interface CreateVehicleUseCase {

    Vehicle handle(CreateVehicleCommand command);
}
