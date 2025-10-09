package com.fiapchallenge.garage.adapters.outbound.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "service_order_service_type")
public class ServiceOrderServiceTypeEntity {

    @EmbeddedId
    private ServiceOrderServiceTypeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceOrderId")
    @JoinColumn(name = "service_order_id", insertable = false, updatable = false)
    private ServiceOrderEntity serviceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceTypeId")
    @JoinColumn(name = "service_type_id", insertable = false, updatable = false)
    private ServiceTypeEntity serviceType;

    public ServiceOrderServiceTypeEntity() {
    }

    public ServiceOrderServiceTypeEntity(ServiceOrderEntity serviceOrder, ServiceTypeEntity serviceType) {
        this.serviceOrder = serviceOrder;
        this.serviceType = serviceType;
        this.id = new ServiceOrderServiceTypeId(serviceOrder.getId(), serviceType.getId());
    }

    public ServiceTypeEntity getServiceType() {
        return this.serviceType;
    }
}