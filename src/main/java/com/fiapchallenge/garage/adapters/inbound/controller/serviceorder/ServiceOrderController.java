package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.mapper.ServiceOrderMapper;
import com.fiapchallenge.garage.application.serviceorder.addservicetypes.AddServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.addstockitems.AddStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.cancel.CancelServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.complete.CompleteServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.deliver.DeliverServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.finishdiagnosis.FinishServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.get.GetServiceOrderDetailsUseCase;
import com.fiapchallenge.garage.application.serviceorder.list.ListActiveServiceOrdersUseCase;
import com.fiapchallenge.garage.application.serviceorder.removeservicetypes.RemoveServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.removestockitems.RemoveStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.startsdiagnosis.StartServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorder.addservicetypes.AddServiceTypesCommand;
import com.fiapchallenge.garage.application.serviceorder.addstockitems.AddStockItemsCommand;
import com.fiapchallenge.garage.application.serviceorder.cancel.CancelServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.complete.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.deliver.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.finishdiagnosis.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.get.GetServiceOrderDetailsCommand;
import com.fiapchallenge.garage.application.serviceorder.removeservicetypes.RemoveServiceTypesCommand;
import com.fiapchallenge.garage.application.serviceorder.removestockitems.RemoveStockItemsCommand;
import com.fiapchallenge.garage.application.serviceorder.startsdiagnosis.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController implements ServiceOrderControllerOpenApiSpec {

    private final CreateServiceOrderUseCase createServiceOrderUseCase;
    private final StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase;
    private final FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase;
    private final StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase;
    private final CompleteServiceOrderUseCase completeServiceOrderUseCase;
    private final DeliverServiceOrderUseCase deliverServiceOrderUseCase;
    private final CancelServiceOrderUseCase cancelServiceOrderUseCase;
    private final GetServiceOrderDetailsUseCase getServiceOrderDetailsUseCase;
    private final AddStockItemsUseCase addStockItemsUseCase;
    private final RemoveStockItemsUseCase removeStockItemsUseCase;
    private final AddServiceTypesUseCase addServiceTypesUseCase;
    private final RemoveServiceTypesUseCase removeServiceTypesUseCase;
    private final ListActiveServiceOrdersUseCase listActiveServiceOrdersUseCase;

    public ServiceOrderController(CreateServiceOrderUseCase createServiceOrderUseCase,
                                  StartServiceOrderDiagnosticUseCase startServiceOrderDiagnosticUseCase,
                                  FinishServiceOrderDiagnosticUseCase finishServiceOrderDiagnosticUseCase,
                                  StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase,
                                  CompleteServiceOrderUseCase completeServiceOrderUseCase,
                                  DeliverServiceOrderUseCase deliverServiceOrderUseCase,
                                  CancelServiceOrderUseCase cancelServiceOrderUseCase,
                                  GetServiceOrderDetailsUseCase getServiceOrderDetailsUseCase,
                                  AddStockItemsUseCase addStockItemsUseCase,
                                  RemoveStockItemsUseCase removeStockItemsUseCase,
                                  AddServiceTypesUseCase addServiceTypesUseCase,
                                  RemoveServiceTypesUseCase removeServiceTypesUseCase,
                                  ListActiveServiceOrdersUseCase listActiveServiceOrdersUseCase) {

        this.createServiceOrderUseCase = createServiceOrderUseCase;
        this.startServiceOrderDiagnosticUseCase = startServiceOrderDiagnosticUseCase;
        this.finishServiceOrderDiagnosticUseCase = finishServiceOrderDiagnosticUseCase;
        this.startServiceOrderExecutionUseCase = startServiceOrderExecutionUseCase;
        this.completeServiceOrderUseCase = completeServiceOrderUseCase;
        this.deliverServiceOrderUseCase = deliverServiceOrderUseCase;
        this.cancelServiceOrderUseCase = cancelServiceOrderUseCase;
        this.getServiceOrderDetailsUseCase = getServiceOrderDetailsUseCase;
        this.addStockItemsUseCase = addStockItemsUseCase;
        this.removeStockItemsUseCase = removeStockItemsUseCase;
        this.addServiceTypesUseCase = addServiceTypesUseCase;
        this.removeServiceTypesUseCase = removeServiceTypesUseCase;
        this.listActiveServiceOrdersUseCase = listActiveServiceOrdersUseCase;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<ServiceOrderResponseDTO> create(@Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO) {
        ServiceOrder serviceOrder = createServiceOrderUseCase.handle(new CreateServiceOrderCommand(createServiceOrderDTO));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @PostMapping("/{id}/start-diagnosis")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> startDiagnosis(@PathVariable UUID id) {
        ServiceOrder serviceOrder = startServiceOrderDiagnosticUseCase.handle(new StartServiceOrderDiagnosticCommand(id));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @PostMapping("/{id}/finish-diagnosis")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> finishDiagnosis(@PathVariable UUID id) {
        ServiceOrder serviceOrder = finishServiceOrderDiagnosticUseCase.handle(new FinishServiceOrderDiagnosticCommand(id));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @PostMapping("/{id}/start-execution")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> startExecution(@PathVariable UUID id) {
        ServiceOrder serviceOrder = startServiceOrderExecutionUseCase.handle(new StartServiceOrderExecutionCommand(id));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @PostMapping("/{id}/finish")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> finish(@PathVariable UUID id) {
        ServiceOrder serviceOrder = completeServiceOrderUseCase.handle(new CompleteServiceOrderCommand(id));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @PostMapping("/{id}/deliver")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<ServiceOrderResponseDTO> deliver(@PathVariable UUID id) {
        ServiceOrder serviceOrder = deliverServiceOrderUseCase.handle(new DeliverServiceOrderCommand(id));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<ServiceOrderResponseDTO> setCancelled(@PathVariable UUID id) {
        ServiceOrder serviceOrder = cancelServiceOrderUseCase.handle(new CancelServiceOrderCommand(id));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> getServiceOrderDetails(@PathVariable UUID id) {
        ServiceOrder serviceOrder = getServiceOrderDetailsUseCase.handle(new GetServiceOrderDetailsCommand(id));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @PostMapping("/{id}/stock-items")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> addStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        ServiceOrder serviceOrder = addStockItemsUseCase.handle(new AddStockItemsCommand(id, stockItems));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @DeleteMapping("/{id}/stock-items")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> removeStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        ServiceOrder serviceOrder = removeStockItemsUseCase.handle(new RemoveStockItemsCommand(id, stockItems));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @PostMapping("/{id}/service-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> addServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        ServiceOrder serviceOrder = addServiceTypesUseCase.handle(new AddServiceTypesCommand(id, serviceTypeIds));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @DeleteMapping("/{id}/service-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> removeServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        ServiceOrder serviceOrder = removeServiceTypesUseCase.handle(new RemoveServiceTypesCommand(id, serviceTypeIds));
        return ResponseEntity.ok(ServiceOrderMapper.toResponseDTO(serviceOrder));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<List<ServiceOrderResponseDTO>> listActiveOrders() {
        List<ServiceOrder> serviceOrders = listActiveServiceOrdersUseCase.handle();
        return ResponseEntity.ok(serviceOrders.stream().map(ServiceOrderMapper::toResponseDTO).toList());
    }
}