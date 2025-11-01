package com.fiapchallenge.garage.adapters.inbound.controller.servicetype;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeRequestDTO;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.command.CreateServiceTypeCommand;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeController implements ServiceTypeOpenApiSpec {

    private final CreateServiceTypeUseCase createServiceTypeUseCase;
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeController(CreateServiceTypeUseCase createServiceTypeUseCase, ServiceTypeRepository serviceTypeRepository) {
        this.createServiceTypeUseCase = createServiceTypeUseCase;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    @PostMapping
    public ResponseEntity<ServiceType> create(@Valid @RequestBody ServiceTypeRequestDTO serviceTypeRequestDTO) {
        CreateServiceTypeCommand command = new CreateServiceTypeCommand(
                serviceTypeRequestDTO.description(),
                serviceTypeRequestDTO.value()
        );
        ServiceType serviceType = createServiceTypeUseCase.handle(command);
        return ResponseEntity.ok(serviceType);
    }

    @GetMapping
    public ResponseEntity<List<ServiceType>> getAll() {
        List<ServiceType> serviceTypes = serviceTypeRepository.findAll();
        return ResponseEntity.ok(serviceTypes);
    }
}
