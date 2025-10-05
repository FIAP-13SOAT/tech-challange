package com.fiapchallenge.garage.adapters.outbound.repositories;

import com.fiapchallenge.garage.adapters.outbound.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaVehicleRepository extends JpaRepository<VehicleEntity, UUID> {
}
