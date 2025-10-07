package com.fiapchallenge.garage.adapters.inbound.controller.vehicle;

import com.fiapchallenge.garage.application.service.VehicleService;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController implements VehicleControllerOpenApiSpec {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Vehicle> create(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleService.create(vehicleRequestDTO);
        return ResponseEntity.ok(vehicle);
    }
}
