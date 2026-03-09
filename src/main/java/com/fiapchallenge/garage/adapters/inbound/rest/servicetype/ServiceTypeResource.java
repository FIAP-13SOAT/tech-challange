package com.fiapchallenge.garage.adapters.inbound.rest.servicetype;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.UpdateServiceTypeDTO;
import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.delete.DeleteServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.update.UpdateServiceTypeUseCase;
import com.fiapchallenge.garage.controllers.servicetype.ServiceTypeController;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeGateway;
import com.fiapchallenge.garage.presenters.servicetype.ServiceTypePresenter;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeResource implements ServiceTypeResourceOpenApiSpec {

    private final ServiceTypeController serviceTypeController;

    public ServiceTypeResource(
            CreateServiceTypeUseCase createServiceTypeUseCase,
            UpdateServiceTypeUseCase updateServiceTypeUseCase,
            DeleteServiceTypeUseCase deleteServiceTypeUseCase,
            ServiceTypeGateway serviceTypeGateway
    ) {
        this.serviceTypeController = new ServiceTypeController(
                serviceTypeGateway,
                new ServiceTypePresenter(),
                createServiceTypeUseCase,
                updateServiceTypeUseCase,
                deleteServiceTypeUseCase
        );
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<ServiceTypeDTO> create(@Valid @RequestBody ServiceTypeRequestDTO serviceTypeRequestDTO) {
        return ResponseEntity.ok(serviceTypeController.create(serviceTypeRequestDTO));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ServiceTypeDTO>> getAll() {
        return ResponseEntity.ok(serviceTypeController.getAll());
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<ServiceTypeDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateServiceTypeDTO updateServiceTypeDTO) {
        return ResponseEntity.ok(serviceTypeController.update(id, updateServiceTypeDTO));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        serviceTypeController.delete(id);
        return ResponseEntity.noContent().build();
    }
}
