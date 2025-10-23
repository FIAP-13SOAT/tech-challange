package com.fiapchallenge.garage.adapters.inbound.controller.vehicle;

import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.VehicleRequestDTO;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleUseCase;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.command.CreateVehicleCommand;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleController implements VehicleControllerOpenApiSpec {

    private final CreateVehicleUseCase createVehicleUseCase;

    public VehicleController(CreateVehicleUseCase createVehicleUseCase) {
        this.createVehicleUseCase = createVehicleUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<Vehicle> create(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        CreateVehicleCommand command = new CreateVehicleCommand(
                vehicleRequestDTO.model(),
                vehicleRequestDTO.brand(),
                vehicleRequestDTO.licensePlate(),
                vehicleRequestDTO.color(),
                vehicleRequestDTO.year(),
                vehicleRequestDTO.observations(),
                vehicleRequestDTO.customerId()
        );
        Vehicle vehicle = createVehicleUseCase.handle(command);
        return ResponseEntity.ok(vehicle);
    }
}
