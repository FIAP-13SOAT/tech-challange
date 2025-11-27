package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
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

    @Column(name = "customer_id")
    private UUID customerId;

    @ManyToMany
    @JoinTable(
            name = "service_order_service_type",
            joinColumns = @JoinColumn(name = "service_order_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id"))
    private List<ServiceTypeEntity> serviceTypeList;

    @Enumerated(EnumType.STRING)
    ServiceOrderStatus status;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", insertable = false, updatable = false)
    private VehicleEntity vehicle;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "serviceOrderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceOrderItemEntity> stockItems;

    public ServiceOrderEntity() {}

    public ServiceOrderEntity(ServiceOrder serviceOrder) {
        this.observations = serviceOrder.getObservations();
        this.status = serviceOrder.getStatus();
        this.priority = serviceOrder.getStatus().priority;
        this.vehicleId = serviceOrder.getVehicleId();
        this.customerId = serviceOrder.getCustomerId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<ServiceTypeEntity> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<ServiceTypeEntity> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceOrderStatus status) {
        this.status = status;
        this.priority = status.priority;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    public List<ServiceOrderItemEntity> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<ServiceOrderItemEntity> stockItems) {
        this.stockItems = stockItems;
    }
}
