package com.fiapchallenge.garage.adapters.outbound.repositories.vehicle;

import com.fiapchallenge.garage.adapters.outbound.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaVehicleRepository extends JpaRepository<VehicleEntity, UUID> {

    @Query(
            value = "SELECT customer_id FROM vehicle WHERE id = :vehicleId",
            nativeQuery = true
    )
    UUID findCustomerIdByVehicleId(@Param("vehicleId") UUID vehicleId);
    
    List<VehicleEntity> findByCustomerId(UUID customerId);
}
