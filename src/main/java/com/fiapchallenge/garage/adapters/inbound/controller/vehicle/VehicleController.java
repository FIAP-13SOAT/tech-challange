package com.fiapchallenge.garage.adapters.inbound.controller.vehicle;

import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.UpdateVehicleRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.VehicleRequestDTO;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.DeleteVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.FindVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.ListVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.UpdateVehicleUseCase;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.application.vehicle.command.CreateVehicleCommand;
import com.fiapchallenge.garage.application.vehicle.command.UpdateVehicleCommand;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleController implements VehicleControllerOpenApiSpec {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final FindVehicleUseCase findVehicleUseCase;
    private final ListVehicleUseCase listVehicleUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;

    public VehicleController(CreateVehicleUseCase createVehicleUseCase, FindVehicleUseCase findVehicleUseCase, ListVehicleUseCase listVehicleUseCase, UpdateVehicleUseCase updateVehicleUseCase, DeleteVehicleUseCase deleteVehicleUseCase) {
        this.createVehicleUseCase = createVehicleUseCase;
        this.findVehicleUseCase = findVehicleUseCase;
        this.listVehicleUseCase = listVehicleUseCase;
        this.updateVehicleUseCase = updateVehicleUseCase;
        this.deleteVehicleUseCase = deleteVehicleUseCase;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<Vehicle> create(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        CreateVehicleCommand command = new CreateVehicleCommand(
                vehicleRequestDTO.model(),
                vehicleRequestDTO.brand(),
                vehicleRequestDTO.licensePlate().replace("-", ""),
                vehicleRequestDTO.color(),
                vehicleRequestDTO.year(),
                vehicleRequestDTO.observations(),
                vehicleRequestDTO.customerId()
        );

        Vehicle vehicle = createVehicleUseCase.handle(command);
        return ResponseEntity.ok(vehicle);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<List<Vehicle>> listByCustomer(@RequestParam UUID customerId) {
        List<Vehicle> vehicles = listVehicleUseCase.handle(customerId);
        return ResponseEntity.ok(vehicles);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<Vehicle> findById(@PathVariable UUID id) {
        Vehicle vehicle = findVehicleUseCase.handle(id);
        return ResponseEntity.ok(vehicle);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<Vehicle> update(@PathVariable UUID id, @Valid @RequestBody UpdateVehicleRequestDTO dto) {
        UpdateVehicleCommand command = new UpdateVehicleCommand(
                id,
                dto.model(),
                dto.brand(),
                dto.color(),
                dto.year(),
                dto.observations()
        );

        Vehicle vehicle = updateVehicleUseCase.handle(command);
        return ResponseEntity.ok(vehicle);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteVehicleUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }
}
