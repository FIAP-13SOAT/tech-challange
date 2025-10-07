package com.fiapchallenge.garage.domain.servicetype;

import java.math.BigDecimal;
import java.util.UUID;

public class ServiceType {

    private UUID id;
    private BigDecimal value;
    private String description;

    public ServiceType(ServiceTypeRequestDTO serviceTypeRequestDTO) {
        this.value = serviceTypeRequestDTO.value();
        this.description = serviceTypeRequestDTO.description();
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
}
