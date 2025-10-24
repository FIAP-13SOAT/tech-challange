package com.fiapchallenge.garage.adapters.outbound.repositories.vehicle;

import com.fiapchallenge.garage.adapters.outbound.entities.VehicleEntity;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VehicleRepositoryImpl implements VehicleRepository {

    private final JpaVehicleRepository jpaVehicleRepository;

    public VehicleRepositoryImpl(JpaVehicleRepository jpaVehicleRepository) {
        this.jpaVehicleRepository = jpaVehicleRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity vehicleEntity = new VehicleEntity(vehicle);
        vehicleEntity = jpaVehicleRepository.save(vehicleEntity);

        return new Vehicle(
                vehicleEntity.getId(),
                vehicleEntity.getModel(),
                vehicleEntity.getBrand(),
                vehicleEntity.getLicensePlate(),
                vehicleEntity.getCustomerId(),
                vehicleEntity.getColor(),
                vehicleEntity.getYear(),
                vehicleEntity.getObservations()
        );
    }

    @Override
    public UUID findCustomerIdByVehicleId(UUID vehicleId) {
        return jpaVehicleRepository.findCustomerIdByVehicleId(vehicleId);
    }
}
