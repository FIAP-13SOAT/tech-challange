package com.fiapchallenge.garage.adapters.inbound.controller.servicetype;

import com.fiapchallenge.garage.application.service.ServiceTypeService;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeController implements ServiceTypeOpenApiSpec {

    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ServiceType> create(@Valid @RequestBody ServiceTypeRequestDTO serviceTypeRequestDTO) {
        ServiceType serviceType = serviceTypeService.create(serviceTypeRequestDTO);
        return ResponseEntity.ok(serviceType);
    }
}
