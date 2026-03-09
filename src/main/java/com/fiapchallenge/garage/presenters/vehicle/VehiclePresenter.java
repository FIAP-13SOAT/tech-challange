package com.fiapchallenge.garage.presenters.vehicle;

import com.fiapchallenge.garage.adapters.inbound.rest.vehicle.dto.VehicleDTO;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;

import java.util.List;

public class VehiclePresenter {

    public VehicleDTO present(Vehicle vehicle) {
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getModel(),
                vehicle.getBrand(),
                vehicle.getLicensePlate(),
                vehicle.getCustomerId(),
                vehicle.getColor(),
                vehicle.getYear(),
                vehicle.getObservations()
        );
    }

    public List<VehicleDTO> present(List<Vehicle> vehicles) {
        return vehicles.stream().map(this::present).toList();
    }
}
