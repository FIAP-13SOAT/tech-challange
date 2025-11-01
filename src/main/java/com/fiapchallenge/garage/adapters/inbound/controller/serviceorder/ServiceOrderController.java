package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.application.serviceorder.CancelServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.CompleteServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.DeliverServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.FinishServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderProgressUseCase;
import com.fiapchallenge.garage.application.serviceorder.command.CancelServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderProgressCommand;
import com.fiapchallenge.garage.application.serviceorder.command.StockItemCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController implements ServiceOrderControllerOpenApiSpec {

    private final CreateServiceOrderUseCase createServiceOrderUseCase;
    private final StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase;
    private final FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase;
    private final StartServiceOrderProgressUseCase startServiceOrderProgressUseCase;
    private final CompleteServiceOrderUseCase completeServiceOrderUseCase;
    private final DeliverServiceOrderUseCase deliverServiceOrderUseCase;
    private final CancelServiceOrderUseCase cancelServiceOrderUseCase;

    public ServiceOrderController(CreateServiceOrderUseCase createServiceOrderUseCase,
                                 StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase,
                                 FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase,
                                 StartServiceOrderProgressUseCase startServiceOrderProgressUseCase,
                                 CompleteServiceOrderUseCase completeServiceOrderUseCase,
                                 DeliverServiceOrderUseCase deliverServiceOrderUseCase,
                                 CancelServiceOrderUseCase cancelServiceOrderUseCase) {
        this.createServiceOrderUseCase = createServiceOrderUseCase;
        this.startServiceOrderDiagnosticUseCase = startServiceOrderDiagnosticUseCase;
        this.finishServiceOrderDiagnosticUseCase = finishServiceOrderDiagnosticUseCase;
        this.startServiceOrderProgressUseCase = startServiceOrderProgressUseCase;
        this.completeServiceOrderUseCase = completeServiceOrderUseCase;
        this.deliverServiceOrderUseCase = deliverServiceOrderUseCase;
        this.cancelServiceOrderUseCase = cancelServiceOrderUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<ServiceOrder> create(@Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO) {
        List<StockItemCommand> stockItems = createServiceOrderDTO.stockItems() != null ? 
                createServiceOrderDTO.stockItems().stream()
                        .map(item -> new StockItemCommand(item.stockId(), item.quantity()))
                        .toList() : List.<StockItemCommand>of();
                        
        CreateServiceOrderCommand command = new CreateServiceOrderCommand(
                createServiceOrderDTO.observations(),
                createServiceOrderDTO.vehicleId(),
                createServiceOrderDTO.serviceTypeIdList(),
                stockItems
        );
        ServiceOrder serviceOrder = createServiceOrderUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/in-diagnosis")
    public ResponseEntity<ServiceOrder> setInDiagnosis(@PathVariable UUID id) {
        StartServiceOrderDiagnosticCommand command = new StartServiceOrderDiagnosticCommand(id);
        ServiceOrder serviceOrder = startServiceOrderDiagnosticUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/awaiting-approval")
    public ResponseEntity<ServiceOrder> setAwaitingApproval(@PathVariable UUID id) {
        FinishServiceOrderDiagnosticCommand command = new FinishServiceOrderDiagnosticCommand(id);
        ServiceOrder serviceOrder = finishServiceOrderDiagnosticUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/in-progress")
    public ResponseEntity<ServiceOrder> setInProgress(@PathVariable UUID id) {
        StartServiceOrderProgressCommand command = new StartServiceOrderProgressCommand(id);
        ServiceOrder serviceOrder = startServiceOrderProgressUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/completed")
    public ResponseEntity<ServiceOrder> setCompleted(@PathVariable UUID id) {
        CompleteServiceOrderCommand command = new CompleteServiceOrderCommand(id);
        ServiceOrder serviceOrder = completeServiceOrderUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/delivered")
    public ResponseEntity<ServiceOrder> setDelivered(@PathVariable UUID id) {
        DeliverServiceOrderCommand command = new DeliverServiceOrderCommand(id);
        ServiceOrder serviceOrder = deliverServiceOrderUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/cancelled")
    public ResponseEntity<ServiceOrder> setCancelled(@PathVariable UUID id) {
        CancelServiceOrderCommand command = new CancelServiceOrderCommand(id);
        ServiceOrder serviceOrder = cancelServiceOrderUseCase.handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @GetMapping("/status")
    public ResponseEntity<List<ServiceOrderStatus>> getAllStatus() {
        return ResponseEntity.ok(Arrays.asList(ServiceOrderStatus.values()));
    }
}
