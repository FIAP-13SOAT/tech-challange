package com.fiapchallenge.garage.domain.quote;

import java.math.BigDecimal;
import java.util.UUID;

public class Quote {

    private final UUID id;
    private final UUID serviceOrderId;
    private final BigDecimal value;
    private final UUID customerId;

    public Quote(UUID id, UUID customerId, UUID serviceOrderId, BigDecimal value) {
        this.id = id;
        this.customerId = customerId;
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

    public UUID getCustomerId() {
        return customerId;
    }
}
