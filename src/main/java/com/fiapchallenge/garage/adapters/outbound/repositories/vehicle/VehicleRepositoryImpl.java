package com.fiapchallenge.garage.adapters.outbound.repositories.vehicle;

import com.fiapchallenge.garage.adapters.outbound.entities.VehicleEntity;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    
    @Override
    public List<Vehicle> findByCustomerId(UUID customerId) {
        return jpaVehicleRepository.findByCustomerId(customerId)
                .stream()
                .map(entity -> new Vehicle(
                        entity.getId(),
                        entity.getModel(),
                        entity.getBrand(),
                        entity.getLicensePlate(),
                        entity.getCustomerId(),
                        entity.getColor(),
                        entity.getYear(),
                        entity.getObservations()
                ))
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Vehicle> findById(UUID id) {
        return jpaVehicleRepository.findById(id)
                .map(entity -> new Vehicle(
                        entity.getId(),
                        entity.getModel(),
                        entity.getBrand(),
                        entity.getLicensePlate(),
                        entity.getCustomerId(),
                        entity.getColor(),
                        entity.getYear(),
                        entity.getObservations()
                ));
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
        
        return new Vehicle(
                entity.getId(),
                entity.getModel(),
                entity.getBrand(),
                entity.getLicensePlate(),
                entity.getCustomerId(),
                entity.getColor(),
                entity.getYear(),
                entity.getObservations()
        );
    }
    
    @Override
    public void deleteById(UUID id) {
        jpaVehicleRepository.deleteById(id);
    }
}
