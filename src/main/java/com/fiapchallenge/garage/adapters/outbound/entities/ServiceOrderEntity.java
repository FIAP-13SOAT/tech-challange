package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "service_order")
@Entity
public class ServiceOrderEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    private ServiceOrderStatus status;
    private UUID vehicleId;
}
