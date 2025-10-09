package com.fiapchallenge.garage.adapters.outbound.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ServiceOrderServiceTypeId implements Serializable {

    private UUID serviceOrderId;
    private UUID serviceTypeId;

    public ServiceOrderServiceTypeId() {}

    public ServiceOrderServiceTypeId(UUID serviceOrderId, UUID serviceTypeId) {
        this.serviceOrderId = serviceOrderId;
        this.serviceTypeId = serviceTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceOrderServiceTypeId that)) return false;
        return Objects.equals(serviceOrderId, that.serviceOrderId)
                && Objects.equals(serviceTypeId, that.serviceTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceOrderId, serviceTypeId);
    }
}