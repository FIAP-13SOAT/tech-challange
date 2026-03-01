package com.fiapchallenge.garage.unit.vehicle;

import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import com.fiapchallenge.garage.unit.vehicle.factory.VehicleTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleUnitTest {

    @Mock
    private VehicleGateway vehicleGateway;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateVehicleService createVehicleService;

    @Test
    @DisplayName("Criar veículo")
    void shouldCreateVehicle() {
        when(customerRepository.exists(VehicleTestFactory.CUSTOMER_ID)).thenReturn(true);
        when(vehicleGateway.save(any(Vehicle.class))).thenReturn(VehicleTestFactory.build());

        Vehicle vehicle = createVehicleService.handle(VehicleTestFactory.buildCommand());

        assertNotNull(vehicle);
        assertEquals(VehicleTestFactory.ID, vehicle.getId());
        assertEquals(VehicleTestFactory.BRAND, vehicle.getBrand());
        assertEquals(VehicleTestFactory.MODEL, vehicle.getModel());
        assertEquals(VehicleTestFactory.LICENSE_PLATE, vehicle.getLicensePlate());
        assertEquals(VehicleTestFactory.COLOR, vehicle.getColor());
        assertEquals(VehicleTestFactory.YEAR, vehicle.getYear());
        assertEquals(VehicleTestFactory.OBSERVATIONS, vehicle.getObservations());
        assertEquals(VehicleTestFactory.CUSTOMER_ID, vehicle.getCustomerId());
    }
}
