package com.fiapchallenge.garage.domain.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CreateServiceOrderCommand;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.util.List;
import java.util.UUID;

public class ServiceOrder {

    private UUID id;
    private String observations;
    private UUID vehicleId;
    private ServiceOrderStatus status;
    private List<ServiceType> serviceTypeList;

    public ServiceOrder(CreateServiceOrderCommand command) {
        this.observations = command.observations();
        this.vehicleId = command.vehicleId();
        this.status = ServiceOrderStatus.CREATED;
    }

    public ServiceOrder(UUID id, String observations, UUID vehicleId, ServiceOrderStatus status, List<ServiceType> serviceTypeList) {
        this.id = id;
        this.observations = observations;
        this.vehicleId = vehicleId;
        this.status = status;
        this.serviceTypeList = serviceTypeList;
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

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public List<ServiceType> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<ServiceType> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
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

    public void startExecution() {
        if (this.status != ServiceOrderStatus.AWAITING_APPROVAL) {
            throw new IllegalStateException("Service order must be in AWAITING_APPROVAL status to be approved.");
        }
        this.status = ServiceOrderStatus.IN_PROGRESS;
    }

    public void finishExecution() {
        if (this.status != ServiceOrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Service order must be in IN_PROGRESS status to be approved.");
        }
        this.status = ServiceOrderStatus.COMPLETED;
    }

}
