package com.fiapchallenge.garage.controllers.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderStatusDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;
import com.fiapchallenge.garage.application.serviceorder.addservicetypes.AddServiceTypesCommand;
import com.fiapchallenge.garage.application.serviceorder.addservicetypes.AddServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.addstockitems.AddStockItemsCommand;
import com.fiapchallenge.garage.application.serviceorder.addstockitems.AddStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.cancel.CancelServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.cancel.CancelServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.complete.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.complete.CompleteServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.deliver.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.application.serviceorder.deliver.DeliverServiceOrderUseCase;
import com.fiapchallenge.garage.application.serviceorder.finishdiagnosis.FinishServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.finishdiagnosis.FinishServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorder.get.GetServiceOrderDetailsCommand;
import com.fiapchallenge.garage.application.serviceorder.get.GetServiceOrderDetailsUseCase;
import com.fiapchallenge.garage.application.serviceorder.getstatus.GetServiceOrderStatusCommand;
import com.fiapchallenge.garage.application.serviceorder.getstatus.GetServiceOrderStatusUseCase;
import com.fiapchallenge.garage.application.serviceorder.list.ListActiveServiceOrdersUseCase;
import com.fiapchallenge.garage.application.serviceorder.removeservicetypes.RemoveServiceTypesCommand;
import com.fiapchallenge.garage.application.serviceorder.removeservicetypes.RemoveServiceTypesUseCase;
import com.fiapchallenge.garage.application.serviceorder.removestockitems.RemoveStockItemsCommand;
import com.fiapchallenge.garage.application.serviceorder.removestockitems.RemoveStockItemsUseCase;
import com.fiapchallenge.garage.application.serviceorder.startsdiagnosis.StartServiceOrderDiagnosticCommand;
import com.fiapchallenge.garage.application.serviceorder.startsdiagnosis.StartServiceOrderDiagnosticUseCase;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionCommand;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.presenters.serviceorder.ServiceOrderPresenter;

import java.util.List;
import java.util.UUID;

public class ServiceOrderController {

    private final ServiceOrderPresenter serviceOrderPresenter;
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
    private final GetServiceOrderStatusUseCase getServiceOrderStatusUseCase;

    public ServiceOrderController(
            ServiceOrderPresenter serviceOrderPresenter,
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
        this.serviceOrderPresenter = serviceOrderPresenter;
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
        this.getServiceOrderStatusUseCase = getServiceOrderStatusUseCase;
    }

    public ServiceOrderResponseDTO create(CreateServiceOrderDTO createServiceOrderDTO) {
        return serviceOrderPresenter.present(createServiceOrderUseCase.handle(new CreateServiceOrderCommand(createServiceOrderDTO)));
    }

    public ServiceOrderResponseDTO startDiagnosis(UUID id) {
        return serviceOrderPresenter.present(startServiceOrderDiagnosticUseCase.handle(new StartServiceOrderDiagnosticCommand(id)));
    }

    public ServiceOrderResponseDTO finishDiagnosis(UUID id) {
        return serviceOrderPresenter.present(finishServiceOrderDiagnosticUseCase.handle(new FinishServiceOrderDiagnosticCommand(id)));
    }

    public ServiceOrderResponseDTO startExecution(UUID id) {
        return serviceOrderPresenter.present(startServiceOrderExecutionUseCase.handle(new StartServiceOrderExecutionCommand(id)));
    }

    public ServiceOrderResponseDTO finish(UUID id) {
        return serviceOrderPresenter.present(completeServiceOrderUseCase.handle(new CompleteServiceOrderCommand(id)));
    }

    public ServiceOrderResponseDTO deliver(UUID id) {
        return serviceOrderPresenter.present(deliverServiceOrderUseCase.handle(new DeliverServiceOrderCommand(id)));
    }

    public ServiceOrderResponseDTO setCancelled(UUID id) {
        return serviceOrderPresenter.present(cancelServiceOrderUseCase.handle(new CancelServiceOrderCommand(id)));
    }

    public ServiceOrderResponseDTO getServiceOrderDetails(UUID id) {
        return serviceOrderPresenter.present(getServiceOrderDetailsUseCase.handle(new GetServiceOrderDetailsCommand(id)));
    }

    public ServiceOrderResponseDTO addStockItems(UUID id, List<StockItemDTO> stockItems) {
        return serviceOrderPresenter.present(addStockItemsUseCase.handle(new AddStockItemsCommand(id, stockItems)));
    }

    public ServiceOrderResponseDTO removeStockItems(UUID id, List<StockItemDTO> stockItems) {
        return serviceOrderPresenter.present(removeStockItemsUseCase.handle(new RemoveStockItemsCommand(id, stockItems)));
    }

    public ServiceOrderResponseDTO addServiceTypes(UUID id, List<UUID> serviceTypeIds) {
        return serviceOrderPresenter.present(addServiceTypesUseCase.handle(new AddServiceTypesCommand(id, serviceTypeIds)));
    }

    public ServiceOrderResponseDTO removeServiceTypes(UUID id, List<UUID> serviceTypeIds) {
        return serviceOrderPresenter.present(removeServiceTypesUseCase.handle(new RemoveServiceTypesCommand(id, serviceTypeIds)));
    }

    public ServiceOrderStatusDTO getServiceOrderStatus(UUID id) {
        return getServiceOrderStatusUseCase.handle(new GetServiceOrderStatusCommand(id));
    }

    public List<ServiceOrderResponseDTO> listActiveOrders() {
        return serviceOrderPresenter.present(listActiveServiceOrdersUseCase.handle());
    }
}
