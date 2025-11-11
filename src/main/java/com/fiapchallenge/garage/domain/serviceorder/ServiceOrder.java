package com.fiapchallenge.garage.domain.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.util.List;
import java.util.UUID;

public class ServiceOrder {

    private UUID id;
    private String observations;
    private UUID vehicleId;
    private UUID customerId;
    private ServiceOrderStatus status;
    private List<ServiceType> serviceTypeList;
    private List<ServiceOrderItem> stockItems;

    public ServiceOrder(CreateServiceOrderCommand command) {
        this.observations = command.observations();
        this.vehicleId = command.vehicleId();
        this.customerId = command.customerId();
        this.status = ServiceOrderStatus.CREATED;
    }

    public ServiceOrder(UUID id, String observations, UUID vehicleId, UUID customerId, ServiceOrderStatus status, List<ServiceType> serviceTypeList, List<ServiceOrderItem> stockItems) {
        this.id = id;
        this.observations = observations;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.status = status;
        this.serviceTypeList = serviceTypeList;
        this.stockItems = stockItems;
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

    public UUID getCustomerId() {
        return customerId;
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
        if (this.status != ServiceOrderStatus.CREATED) {
            throw new IllegalStateException("Service order must be in CREATED status to start diagnostic.");
        }
        this.status = ServiceOrderStatus.IN_DIAGNOSIS;
    }

    public void sendToApproval() {
        if (this.status != ServiceOrderStatus.IN_DIAGNOSIS) {
            throw new IllegalStateException("Service order must be in IN_DIAGNOSIS status to finish diagnostic.");
        }
        this.status = ServiceOrderStatus.AWAITING_APPROVAL;
    }

    public void cancel() {
        if (this.status == ServiceOrderStatus.COMPLETED || this.status == ServiceOrderStatus.DELIVERED || this.status == ServiceOrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel service order in status: " + this.status);
        }
        this.status = ServiceOrderStatus.CANCELLED;
    }

    public void startProgress() {
        if (this.status != ServiceOrderStatus.AWAITING_APPROVAL) {
            throw new IllegalStateException("Service order must be in AWAITING_APPROVAL status to start progress.");
        }
        this.status = ServiceOrderStatus.IN_PROGRESS;
    }

    public void complete() {
        if (this.status != ServiceOrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Service order must be in IN_PROGRESS status to complete.");
        }
        this.status = ServiceOrderStatus.COMPLETED;
    }

    public void deliver() {
        if (this.status != ServiceOrderStatus.COMPLETED) {
            throw new IllegalStateException("Service order must be in COMPLETED status to deliver.");
        }
        this.status = ServiceOrderStatus.DELIVERED;
    }

    public void addStockItems(List<ServiceOrderItem> items) {
        if (this.stockItems == null) {
            this.stockItems = new java.util.ArrayList<>();
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
            this.serviceTypeList = new java.util.ArrayList<>();
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
