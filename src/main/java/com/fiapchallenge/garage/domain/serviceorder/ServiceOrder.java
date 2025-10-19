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
}
