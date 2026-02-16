package com.fiapchallenge.garage.adapters.inbound.rest.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderStatusDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;
import com.fiapchallenge.garage.application.serviceorder.addservicetypes.AddServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.addstockitems.AddStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.cancel.CancelServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.complete.CompleteServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.deliver.DeliverServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.finishdiagnosis.FinishServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.get.GetServiceOrderDetailsUseCase;
import com.fiapchallenge.garage.application.serviceorder.getstatus.GetServiceOrderStatusUseCase;
import com.fiapchallenge.garage.application.serviceorder.list.ListActiveServiceOrdersUseCase;
import com.fiapchallenge.garage.application.serviceorder.removeservicetypes.RemoveServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.removestockitems.RemoveStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.startsdiagnosis.StartServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.controllers.serviceorder.ServiceOrderController;
import com.fiapchallenge.garage.presenters.serviceorder.ServiceOrderPresenter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderResource implements ServiceOrderResourceOpenApiSpec {

    private final ServiceOrderController serviceOrderController;

    public ServiceOrderResource(
            CreateServiceOrderUseCase createServiceOrderUseCase,
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
            ListActiveServiceOrdersUseCase listActiveServiceOrdersUseCase,
            GetServiceOrderStatusUseCase getServiceOrderStatusUseCase
    ) {
        this.serviceOrderController = new ServiceOrderController(
                new ServiceOrderPresenter(),
                createServiceOrderUseCase,
                startServiceOrderDiagnosticUseCase,
                finishServiceOrderDiagnosticUseCase,
                startServiceOrderExecutionUseCase,
                completeServiceOrderUseCase,
                deliverServiceOrderUseCase,
                cancelServiceOrderUseCase,
                getServiceOrderDetailsUseCase,
                addStockItemsUseCase,
                removeStockItemsUseCase,
                addServiceTypesUseCase,
                removeServiceTypesUseCase,
                listActiveServiceOrdersUseCase,
                getServiceOrderStatusUseCase
        );
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<ServiceOrderResponseDTO> create(@Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO) {
        return ResponseEntity.ok(serviceOrderController.create(createServiceOrderDTO));
    }

    @Override
    @PostMapping("/{id}/start-diagnosis")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> startDiagnosis(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.startDiagnosis(id));
    }

    @Override
    @PostMapping("/{id}/finish-diagnosis")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> finishDiagnosis(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.finishDiagnosis(id));
    }

    @PostMapping("/{id}/start-execution")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> startExecution(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.startExecution(id));
    }

    @Override
    @PostMapping("/{id}/finish")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> finish(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.finish(id));
    }

    @Override
    @PostMapping("/{id}/deliver")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<ServiceOrderResponseDTO> deliver(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.deliver(id));
    }

    @Override
    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    public ResponseEntity<ServiceOrderResponseDTO> setCancelled(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.setCancelled(id));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> getServiceOrderDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.getServiceOrderDetails(id));
    }

    @Override
    @PostMapping("/{id}/stock-items")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> addStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        return ResponseEntity.ok(serviceOrderController.addStockItems(id, stockItems));
    }

    @Override
    @DeleteMapping("/{id}/stock-items")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> removeStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems) {
        return ResponseEntity.ok(serviceOrderController.removeStockItems(id, stockItems));
    }

    @Override
    @PostMapping("/{id}/service-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> addServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        return ResponseEntity.ok(serviceOrderController.addServiceTypes(id, serviceTypeIds));
    }

    @Override
    @DeleteMapping("/{id}/service-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<ServiceOrderResponseDTO> removeServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds) {
        return ResponseEntity.ok(serviceOrderController.removeServiceTypes(id, serviceTypeIds));
    }

    @GetMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<ServiceOrderStatusDTO> getServiceOrderStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderController.getServiceOrderStatus(id));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK', 'MECHANIC')")
    public ResponseEntity<List<ServiceOrderResponseDTO>> listActiveOrders() {
        return ResponseEntity.ok(serviceOrderController.listActiveOrders());
    }
}
