package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Table(name = "service_order")
@Entity
public class ServiceOrderEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String observations;

    @Column(name = "vehicle_id")
    private UUID vehicleId;

    @ManyToMany
    @JoinTable(
            name = "service_order_service_type",
            joinColumns = @JoinColumn(name = "service_order_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id")
    )
    private List<ServiceTypeEntity> serviceTypeList;

    @Enumerated(EnumType.STRING)
    ServiceOrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", insertable = false, updatable = false)
    private VehicleEntity vehicle;

    public ServiceOrderEntity() {
    }

    public ServiceOrderEntity(ServiceOrder serviceOrder) {
        this.id = serviceOrder.getId();
        this.observations = serviceOrder.getObservations();
        this.status = serviceOrder.getStatus();
        this.vehicleId = serviceOrder.getVehicleId();
    }

    public UUID getId() {
        return id;
    }

    public ServiceOrderEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getObservations() {
        return observations;
    }

    public ServiceOrderEntity setObservations(String observations) {
        this.observations = observations;
        return this;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public ServiceOrderEntity setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public List<ServiceTypeEntity> getServiceTypeList() {
        return serviceTypeList;
    }

    public ServiceOrderEntity setServiceTypeList(List<ServiceTypeEntity> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
        return this;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public ServiceOrderEntity setStatus(ServiceOrderStatus status) {
        this.status = status;
        return this;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public ServiceOrderEntity setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
        return this;
    }
}
