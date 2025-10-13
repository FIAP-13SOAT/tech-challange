package com.fiapchallenge.garage.domain.quote;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

import java.math.BigDecimal;
import java.util.UUID;

public class Quote {

    private final UUID id;
    private final UUID serviceOrderId;
    private final BigDecimal value;

    public Quote(UUID id, UUID serviceOrderId, BigDecimal value) {
        this.id = id;
        this.serviceOrderId = serviceOrderId;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public UUID getServiceOrderId() {
        return serviceOrderId;
    }

    public BigDecimal getValue() {
        return value;
    }
}
