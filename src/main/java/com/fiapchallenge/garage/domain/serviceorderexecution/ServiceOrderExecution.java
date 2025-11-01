package com.fiapchallenge.garage.domain.serviceorderexecution;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceOrderExecution {

    private final UUID serviceOrderId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long executionTime;


    public ServiceOrderExecution(UUID serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }
    public ServiceOrderExecution(UUID serviceOrderId, LocalDateTime startDate, LocalDateTime endDate, long executionTime) {
        this.serviceOrderId = serviceOrderId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.executionTime = executionTime;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public UUID getServiceOrderId() {
        return serviceOrderId;
    }

    public void start() {
        this.startDate = LocalDateTime.now();
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void finish() {
        this.endDate = LocalDateTime.now();
        this.executionTime = Duration.between(startDate, endDate).toMillis();
    }

}
