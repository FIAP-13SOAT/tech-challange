package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.application.serviceorder.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController implements ServiceOrderControllerOpenApiSpec {

    CreateServiceOrderUseCase createServiceOrderUseCase;

    public ServiceOrderController(CreateServiceOrderUseCase createServiceOrderUseCase) {
        this.createServiceOrderUseCase = createServiceOrderUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<ServiceOrder> create(@Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO) {
        CreateServiceOrderCommand command = new CreateServiceOrderCommand(
                createServiceOrderDTO.observations(),
                createServiceOrderDTO.vehicleId(),
                createServiceOrderDTO.serviceTypeIdList()
        );
        ServiceOrder serviceOrder = createServiceOrderUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }
}
