package com.fiapchallenge.garage.adapters.inbound.rest.vehicle;

import com.fiapchallenge.garage.adapters.inbound.rest.vehicle.dto.UpdateVehicleRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.vehicle.dto.VehicleDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.vehicle.dto.VehicleRequestDTO;
import com.fiapchallenge.garage.controllers.vehicle.VehicleController;
import com.fiapchallenge.garage.domain.customer.CustomerGateway;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import com.fiapchallenge.garage.presenters.vehicle.VehiclePresenter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleResource implements VehicleResourceOpenApiSpec {

    private final VehicleController vehicleController;

    public VehicleResource(VehicleGateway vehicleGateway, CustomerGateway customerGateway) {
        this.vehicleController = new VehicleController(vehicleGateway, customerGateway, new VehiclePresenter());
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return ResponseEntity.ok(vehicleController.create(vehicleRequestDTO));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<List<VehicleDTO>> listByCustomer(@RequestParam UUID customerId) {
        return ResponseEntity.ok(vehicleController.listByCustomer(customerId));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<VehicleDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(vehicleController.findById(id));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<VehicleDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateVehicleRequestDTO dto) {
        return ResponseEntity.ok(vehicleController.update(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        vehicleController.delete(id);
        return ResponseEntity.noContent().build();
    }
}
