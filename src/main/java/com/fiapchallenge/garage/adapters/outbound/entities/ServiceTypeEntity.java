package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "service_type")
@Entity
public class ServiceTypeEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    private BigDecimal value;

    public ServiceTypeEntity() {
    }

    public ServiceTypeEntity(ServiceType serviceType) {
        this.description = serviceType.getDescription();
        this.value = serviceType.getValue();
    }

    public UUID getId() {
        return id;
    }

    public ServiceTypeEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ServiceTypeEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public ServiceTypeEntity setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }
}
