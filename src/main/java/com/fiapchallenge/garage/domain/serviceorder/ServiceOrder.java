package com.fiapchallenge.garage.domain.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderCommand;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceOrder {

    private UUID id;
    private String observations;
    private UUID vehicleId;
    private ServiceOrderStatus status;
    private List<ServiceType> serviceTypeList;
    private List<ServiceOrderItem> stockItems;
    private Customer customer;

    public ServiceOrder(CreateServiceOrderCommand command, Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.observations = command.observations();
        this.vehicleId = command.vehicleId();
        this.status = ServiceOrderStatus.RECEIVED;
        this.customer = customer;
    }

    public ServiceOrder(UUID id, String observations, UUID vehicleId, ServiceOrderStatus status, List<ServiceType> serviceTypeList, List<ServiceOrderItem> stockItems, Customer customer) {
        this.id = id;
        this.observations = observations;
        this.vehicleId = vehicleId;
        this.status = status;
        this.serviceTypeList = serviceTypeList;
        this.stockItems = stockItems;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public String getObservations() {
        return observations;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public List<ServiceType> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<ServiceType> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public List<ServiceOrderItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<ServiceOrderItem> stockItems) {
        this.stockItems = stockItems;
    }

    public List<UUID> getServiceTypeListIds() {
        return serviceTypeList.stream().map(ServiceType::getId).toList();
    }

    public void startDiagnostic() {
        if (this.status != ServiceOrderStatus.RECEIVED) {
            throw new IllegalStateException("Ordem de serviço deve estar no status recebida para iniciar diagnóstico.");
        }
        this.status = ServiceOrderStatus.IN_DIAGNOSIS;
    }

    public void sendToApproval() {
        if (this.status != ServiceOrderStatus.IN_DIAGNOSIS) {
            throw new IllegalStateException("Ordem de serviço deve estar no status em diagnóstico para finalizar diagnóstico.");
        }
        this.status = ServiceOrderStatus.AWAITING_APPROVAL;
    }

    public void cancel() {
        if (this.status == ServiceOrderStatus.COMPLETED || this.status == ServiceOrderStatus.DELIVERED || this.status == ServiceOrderStatus.CANCELLED) {
            throw new IllegalStateException("Não é possível cancelar uma ordem de serviço que já esteja completada, entregue ou cancelada.");
        }
        this.status = ServiceOrderStatus.CANCELLED;
    }

    public void approve() {
        if (this.status != ServiceOrderStatus.AWAITING_APPROVAL) {
            throw new IllegalStateException("Ordem de serviço deve estar no status aguardando aprovação para aprovar.");
        }
        this.status = ServiceOrderStatus.AWAITING_EXECUTION;
    }

    public void startProgress() {
        if (this.status != ServiceOrderStatus.AWAITING_EXECUTION) {
            throw new IllegalStateException("Ordem de serviço deve estar no status aguardando execução para iniciar execução.");
        }
        this.status = ServiceOrderStatus.IN_PROGRESS;
    }

    public void complete() {
        if (this.status != ServiceOrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Ordem de serviço deve estar no status em execução para concluir.");
        }
        this.status = ServiceOrderStatus.COMPLETED;
    }

    public void deliver() {
        if (this.status != ServiceOrderStatus.COMPLETED) {
            throw new IllegalStateException("Ordem de serviço deve estar no status concluída para entregar.");
        }
        this.status = ServiceOrderStatus.DELIVERED;
    }

    public void addStockItems(List<ServiceOrderItem> items) {
        if (this.stockItems == null) {
            this.stockItems = new ArrayList<>();
        }
        this.stockItems.addAll(items);
    }

    public void removeStockItems(List<ServiceOrderItem> items) {
        if (this.stockItems != null) {
            for (ServiceOrderItem item : items) {
                this.stockItems.removeIf(existing ->
                    existing.getStockId().equals(item.getStockId()) &&
                    existing.getQuantity().equals(item.getQuantity()));
            }
        }
    }

    public void addServiceTypes(List<ServiceType> services) {
        if (this.serviceTypeList == null) {
            this.serviceTypeList = new ArrayList<>();
        }
        this.serviceTypeList.addAll(services);
    }

    public void removeServiceTypes(List<ServiceType> services) {
        if (this.serviceTypeList != null) {
            for (ServiceType service : services) {
                this.serviceTypeList.removeIf(existing -> existing.getId().equals(service.getId()));
            }
        }
    }
}
