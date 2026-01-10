package com.fiapchallenge.garage.domain.servicetype;

import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeCommand;

import java.math.BigDecimal;
import java.util.UUID;

public class ServiceType {

    private UUID id;
    private BigDecimal value;
    private String description;

    public ServiceType(CreateServiceTypeCommand command) {
        this.value = command.value();
        this.description = command.description();
    }

    public ServiceType(UUID id, BigDecimal value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
