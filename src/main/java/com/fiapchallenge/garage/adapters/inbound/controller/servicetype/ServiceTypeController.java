package com.fiapchallenge.garage.adapters.inbound.controller.servicetype;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeRequestDTO;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.command.CreateServiceTypeCommand;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeController implements ServiceTypeOpenApiSpec {

    private final CreateServiceTypeUseCase createServiceTypeUseCase;

    public ServiceTypeController(CreateServiceTypeUseCase createServiceTypeUseCase) {
        this.createServiceTypeUseCase = createServiceTypeUseCase;
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
}
