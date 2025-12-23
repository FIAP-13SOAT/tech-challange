package com.fiapchallenge.garage.adapters.inbound.controller.vehicle.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.VehicleDTO;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;

import java.util.List;

public class VehicleMapper {

    public static VehicleDTO toResponseDTO(Vehicle vehicle) {
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

    public static List<VehicleDTO> toResponseDTOList(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(VehicleMapper::toResponseDTO)
                .toList();
    }
}