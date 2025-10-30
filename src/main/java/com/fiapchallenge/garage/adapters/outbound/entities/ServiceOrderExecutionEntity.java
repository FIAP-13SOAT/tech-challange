package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "service_order_execution")
@Entity
public class ServiceOrderExecutionEntity {

    @Id
    private UUID serviceOrderId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long executionTime;

    public ServiceOrderExecutionEntity(){}

    public ServiceOrderExecutionEntity(ServiceOrderExecution serviceOrderExcecution) {
        this.serviceOrderId = serviceOrderExcecution.getServiceOrderId();
        this.startDate=serviceOrderExcecution.getStartDate();
        this.endDate=serviceOrderExcecution.getEndDate();
        this.executionTime = serviceOrderExcecution.getExecutionTime();

    }

    public UUID getServiceOrderId() {
        return serviceOrderId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public long getExecutionTime() {
        return executionTime;
    }
}
