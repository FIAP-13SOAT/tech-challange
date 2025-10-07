package com.fiapchallenge.garage.domain.serviceorder;

import java.util.UUID;

public class ServiceOrder {

    private UUID id;
    private UUID vehicleId;
    private String description;
    private ServiceOrderStatus status;
}
