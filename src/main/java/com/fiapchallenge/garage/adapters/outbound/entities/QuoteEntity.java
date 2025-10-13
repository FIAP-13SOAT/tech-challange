package com.fiapchallenge.garage.adapters.outbound.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "quote")
public class QuoteEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal value;

    @OneToOne
    private ServiceOrderEntity serviceOrder;

    public  QuoteEntity() {
    }

    public QuoteEntity(BigDecimal value, ServiceOrderEntity serviceOrder) {
        this.value = value;
        this.serviceOrder = serviceOrder;
    }

    public UUID getId() {
        return id;
    }

    public QuoteEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public QuoteEntity setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public ServiceOrderEntity getServiceOrder() {
        return serviceOrder;
    }

    public QuoteEntity setServiceOrder(ServiceOrderEntity serviceOrder) {
        this.serviceOrder = serviceOrder;
        return this;
    }
}
