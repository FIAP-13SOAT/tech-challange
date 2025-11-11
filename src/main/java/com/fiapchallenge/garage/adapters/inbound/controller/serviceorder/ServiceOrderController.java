package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;
import com.fiapchallenge.garage.application.serviceorder.AddServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.AddStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.CancelServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.CompleteServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.DeliverServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.FinishServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.FinishServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorder.GetServiceOrderDetailsUseCase;
import com.fiapchallenge.garage.application.serviceorder.RemoveServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.RemoveStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.command.AddServiceTypesCommand;
import com.fiapchallenge.garage.application.serviceorder.command.AddStockItemsCommand;
import com.fiapchallenge.garage.application.serviceorder.command.CancelServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderExecutionCommand;
import com.fiapchallenge.garage.application.serviceorder.command.GetServiceOrderDetailsCommand;
import com.fiapchallenge.garage.application.serviceorder.command.RemoveServiceTypesCommand;
import com.fiapchallenge.garage.application.serviceorder.command.RemoveStockItemsCommand;
import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.command.StockItemCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController implements ServiceOrderControllerOpenApiSpec {

    private final CreateServiceOrderUseCase createServiceOrderUseCase;
    private final StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase;
    private final FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase;
    private final CompleteServiceOrderUseCase completeServiceOrderUseCase;
    private final DeliverServiceOrderUseCase deliverServiceOrderUseCase;
    private final CancelServiceOrderUseCase cancelServiceOrderUseCase;
    private final GetServiceOrderDetailsUseCase getServiceOrderDetailsUseCase;
    private final AddStockItemsUseCase addStockItemsUseCase;
    private final RemoveStockItemsUseCase removeStockItemsUseCase;
    private final AddServiceTypesUseCase addServiceTypesUseCase;
    private final RemoveServiceTypesUseCase removeServiceTypesUseCase;

    public ServiceOrderController(CreateServiceOrderUseCase createServiceOrderUseCase,
                                  StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase,
                                  FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase,
                                  CompleteServiceOrderUseCase completeServiceOrderUseCase,
                                  DeliverServiceOrderUseCase deliverServiceOrderUseCase,
                                  CancelServiceOrderUseCase cancelServiceOrderUseCase,
                                  GetServiceOrderDetailsUseCase getServiceOrderDetailsUseCase,
                                  AddStockItemsUseCase addStockItemsUseCase,
                                  RemoveStockItemsUseCase removeStockItemsUseCase,
                                  AddServiceTypesUseCase addServiceTypesUseCase,
                                  RemoveServiceTypesUseCase removeServiceTypesUseCase) {

        this.createServiceOrderUseCase = createServiceOrderUseCase;
        this.startServiceOrderDiagnosticUseCase = startServiceOrderDiagnosticUseCase;
        this.finishServiceOrderDiagnosticUseCase = finishServiceOrderDiagnosticUseCase;
        this.completeServiceOrderUseCase = completeServiceOrderUseCase;
        this.deliverServiceOrderUseCase = deliverServiceOrderUseCase;
        this.cancelServiceOrderUseCase = cancelServiceOrderUseCase;
        this.getServiceOrderDetailsUseCase = getServiceOrderDetailsUseCase;
        this.addStockItemsUseCase = addStockItemsUseCase;
        this.removeStockItemsUseCase = removeStockItemsUseCase;
        this.addServiceTypesUseCase = addServiceTypesUseCase;
        this.removeServiceTypesUseCase = removeServiceTypesUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<ServiceOrder> create(@Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO) {
        ServiceOrder serviceOrder = createServiceOrderUseCase.handle(new CreateServiceOrderCommand(createServiceOrderDTO));

        return ResponseEntity.ok(serviceOrder);
    }

    @Override
    @PostMapping("/{id}/start-diagnosis")
    public ResponseEntity<ServiceOrder> startDiagnosis(@PathVariable UUID id) {
        ServiceOrder serviceOrder = startServiceOrderDiagnosticUseCase.handle(new StartServiceOrderDiagnosticCommand(id));

        return ResponseEntity.ok(serviceOrder);
    }

    @Override
    @PostMapping("/{id}/finish-diagnosis")
    public ResponseEntity<ServiceOrder> finishDiagnosis(@PathVariable UUID id) {
        ServiceOrder serviceOrder = finishServiceOrderDiagnosticUseCase.handle(new FinishServiceOrderDiagnosticCommand(id));

        return ResponseEntity.ok(serviceOrder);
    }

    @Override
    @PostMapping("/{id}/finish")
    public ResponseEntity<ServiceOrder> finish(@PathVariable UUID id) {
        ServiceOrder serviceOrder = completeServiceOrderUseCase.handle(new CompleteServiceOrderCommand(id));

        return ResponseEntity.ok(serviceOrder);
    }

    @Override
    @PostMapping("/{id}/deliver")
    public ResponseEntity<ServiceOrder> deliver(@PathVariable UUID id) {
        ServiceOrder serviceOrder = deliverServiceOrderUseCase.handle(new DeliverServiceOrderCommand(id));

        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ServiceOrder> setCancelled(@PathVariable UUID id) {
        ServiceOrder serviceOrder = cancelServiceOrderUseCase.handle(new CancelServiceOrderCommand(id));

        return ResponseEntity.ok(serviceOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrder> getServiceOrderDetails(@PathVariable UUID id) {
        ServiceOrder serviceOrder = getServiceOrderDetailsUseCase.handle(new GetServiceOrderDetailsCommand(id));

        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/stock-items")
    public ResponseEntity<ServiceOrder> addStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        ServiceOrder serviceOrder = addStockItemsUseCase.handle(new AddStockItemsCommand(id, stockItems));

        return ResponseEntity.ok(serviceOrder);
    }

    @DeleteMapping("/{id}/stock-items")
    public ResponseEntity<ServiceOrder> removeStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        ServiceOrder serviceOrder = removeStockItemsUseCase.handle(new RemoveStockItemsCommand(id, stockItems));

        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/service-types")
    public ResponseEntity<ServiceOrder> addServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        ServiceOrder serviceOrder = addServiceTypesUseCase.handle(new AddServiceTypesCommand(id, serviceTypeIds));

        return ResponseEntity.ok(serviceOrder);
    }

    @DeleteMapping("/{id}/service-types")
    public ResponseEntity<ServiceOrder> removeServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        ServiceOrder serviceOrder = removeServiceTypesUseCase.handle(new RemoveServiceTypesCommand(id, serviceTypeIds));

        return ResponseEntity.ok(serviceOrder);
    }
}
