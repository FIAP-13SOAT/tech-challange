package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;
import com.fiapchallenge.garage.application.serviceorder.command.CancelServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.command.FinishServiceOrderDiagnosticCommand;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController implements ServiceOrderControllerOpenApiSpec {

    private final ServiceOrderUseCases useCases;
    private final ServiceOrderRepository serviceOrderRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceOrderController(ServiceOrderUseCases useCases,
                                 ServiceOrderRepository serviceOrderRepository,
                                 ServiceTypeRepository serviceTypeRepository) {
        this.useCases = useCases;
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceTypeRepository = serviceTypeRepository;
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
        ServiceOrder serviceOrder = useCases.getCreateServiceOrderUseCase().handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/in-diagnosis")
    public ResponseEntity<ServiceOrder> setInDiagnosis(@PathVariable UUID id) {
        StartServiceOrderDiagnosticCommand command = new StartServiceOrderDiagnosticCommand(id);
        ServiceOrder serviceOrder = useCases.getStartServiceOrderDiagnosticUseCase().handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/awaiting-approval")
    public ResponseEntity<ServiceOrder> setAwaitingApproval(@PathVariable UUID id) {
        FinishServiceOrderDiagnosticCommand command = new FinishServiceOrderDiagnosticCommand(id);
        ServiceOrder serviceOrder = useCases.getFinishServiceOrderDiagnosticUseCase().handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/completed")
    public ResponseEntity<ServiceOrder> setCompleted(@PathVariable UUID id) {
        CompleteServiceOrderCommand command = new CompleteServiceOrderCommand(id);
        ServiceOrder serviceOrder = useCases.getCompleteServiceOrderUseCase().handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/delivered")
    public ResponseEntity<ServiceOrder> setDelivered(@PathVariable UUID id) {
        DeliverServiceOrderCommand command = new DeliverServiceOrderCommand(id);
        ServiceOrder serviceOrder = useCases.getDeliverServiceOrderUseCase().handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/cancelled")
    public ResponseEntity<ServiceOrder> setCancelled(@PathVariable UUID id) {
        CancelServiceOrderCommand command = new CancelServiceOrderCommand(id);
        ServiceOrder serviceOrder = useCases.getCancelServiceOrderUseCase().handle(command);
        return ResponseEntity.ok(serviceOrder);
    }

    @GetMapping("/status")
    public ResponseEntity<List<ServiceOrderStatus>> getAllStatus() {
        return ResponseEntity.ok(Arrays.asList(ServiceOrderStatus.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrder> getServiceOrderDetails(@PathVariable UUID id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(id);
        return ResponseEntity.ok(serviceOrder);
    }

    @PostMapping("/{id}/stock-items")
    public ResponseEntity<ServiceOrder> addStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(id);
        List<ServiceOrderItem> items = new java.util.ArrayList<>(stockItems.stream()
                .map(item -> new ServiceOrderItem(null, item.stockId(), item.quantity()))
                .toList());
        serviceOrder.addStockItems(items);
        return ResponseEntity.ok(serviceOrderRepository.save(serviceOrder));
    }

    @DeleteMapping("/{id}/stock-items")
    public ResponseEntity<ServiceOrder> removeStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(id);
        List<ServiceOrderItem> items = new java.util.ArrayList<>(stockItems.stream()
                .map(item -> new ServiceOrderItem(null, item.stockId(), item.quantity()))
                .toList());
        serviceOrder.removeStockItems(items);
        return ResponseEntity.ok(serviceOrderRepository.save(serviceOrder));
    }

    @PostMapping("/{id}/service-types")
    public ResponseEntity<ServiceOrder> addServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(id);
        List<ServiceType> serviceTypes = new java.util.ArrayList<>(serviceTypeIds.stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList());
        serviceOrder.addServiceTypes(serviceTypes);
        return ResponseEntity.ok(serviceOrderRepository.save(serviceOrder));
    }

    @DeleteMapping("/{id}/service-types")
    public ResponseEntity<ServiceOrder> removeServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(id);
        List<ServiceType> serviceTypes = new java.util.ArrayList<>(serviceTypeIds.stream()
                .map(serviceTypeRepository::findByIdOrThrow)
                .toList());
        serviceOrder.removeServiceTypes(serviceTypes);
        return ResponseEntity.ok(serviceOrderRepository.save(serviceOrder));
    }
}
