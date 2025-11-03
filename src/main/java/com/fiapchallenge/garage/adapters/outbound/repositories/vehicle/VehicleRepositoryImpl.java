package com.fiapchallenge.garage.adapters.outbound.repositories.vehicle;

import com.fiapchallenge.garage.adapters.outbound.entities.VehicleEntity;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
        return convertToVehicle(vehicleEntity);
    }

    @Override
    public UUID findCustomerIdByVehicleId(UUID vehicleId) {
        return jpaVehicleRepository.findCustomerIdByVehicleId(vehicleId);
    }
    
    @Override
    public List<Vehicle> findByCustomerId(UUID customerId) {
        return jpaVehicleRepository.findByCustomerId(customerId)
                .stream()
                .map(this::convertToVehicle)
                .toList();
    }
    
    @Override
    public Optional<Vehicle> findById(UUID id) {
        return jpaVehicleRepository.findById(id)
                .map(this::convertToVehicle);
    }
    
    @Override
    public Vehicle update(Vehicle vehicle) {
        VehicleEntity entity = jpaVehicleRepository.findById(vehicle.getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        entity.setModel(vehicle.getModel());
        entity.setBrand(vehicle.getBrand());
        entity.setColor(vehicle.getColor());
        entity.setYear(vehicle.getYear());
        entity.setObservations(vehicle.getObservations());
        
        entity = jpaVehicleRepository.save(entity);
        return convertToVehicle(entity);
    }
    
    @Override
    public void deleteById(UUID id) {
        jpaVehicleRepository.deleteById(id);
    }

    private Vehicle convertToVehicle(VehicleEntity entity) {
        return Vehicle.builder()
                .id(entity.getId())
                .model(entity.getModel())
                .brand(entity.getBrand())
                .licensePlate(entity.getLicensePlate())
                .customerId(entity.getCustomerId())
                .color(entity.getColor())
                .year(entity.getYear())
                .observations(entity.getObservations())
                .build();
    }
}
