package com.fiapchallenge.garage.adapters.inbound.controller.servicetype;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.UpdateServiceTypeDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.mapper.ServiceTypeMapper;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.command.CreateServiceTypeCommand;
import com.fiapchallenge.garage.application.servicetype.delete.DeleteServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.delete.DeleteServiceTypeUseCase.DeleteServiceTypeCmd;
import com.fiapchallenge.garage.application.servicetype.update.UpdateServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.update.UpdateServiceTypeUseCase.UpdateServiceTypeCmd;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeController implements ServiceTypeOpenApiSpec {

    private final CreateServiceTypeUseCase createServiceTypeUseCase;
    private final UpdateServiceTypeUseCase updateServiceTypeUseCase;
    private final DeleteServiceTypeUseCase deleteServiceTypeUseCase;
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeController(CreateServiceTypeUseCase createServiceTypeUseCase, UpdateServiceTypeUseCase updateServiceTypeUseCase, DeleteServiceTypeUseCase deleteServiceTypeUseCase, ServiceTypeRepository serviceTypeRepository) {
        this.createServiceTypeUseCase = createServiceTypeUseCase;
        this.updateServiceTypeUseCase = updateServiceTypeUseCase;
        this.deleteServiceTypeUseCase = deleteServiceTypeUseCase;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<ServiceTypeDTO> create(@Valid @RequestBody ServiceTypeRequestDTO serviceTypeRequestDTO) {
        CreateServiceTypeCommand command = new CreateServiceTypeCommand(
                serviceTypeRequestDTO.description(),
                serviceTypeRequestDTO.value()
        );
        ServiceType serviceType = createServiceTypeUseCase.handle(command);
        return ResponseEntity.ok(ServiceTypeMapper.toResponseDTO(serviceType));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ServiceTypeDTO>> getAll() {
        List<ServiceType> serviceTypes = serviceTypeRepository.findAll();
        return ResponseEntity.ok(ServiceTypeMapper.toResponseDTOList(serviceTypes));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<ServiceTypeDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateServiceTypeDTO updateServiceTypeDTO) {
        UpdateServiceTypeCmd cmd = new UpdateServiceTypeCmd(
                id,
                updateServiceTypeDTO.description(),
                updateServiceTypeDTO.value()
        );

        ServiceType serviceType = updateServiceTypeUseCase.handle(cmd);
        return ResponseEntity.ok(ServiceTypeMapper.toResponseDTO(serviceType));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOCK_KEEPER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        DeleteServiceTypeCmd cmd = new DeleteServiceTypeCmd(id);
        deleteServiceTypeUseCase.handle(cmd);

        return ResponseEntity.noContent().build();
    }
}
