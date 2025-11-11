package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto;

public record ServiceOrderTrackingDTO(
    String licensePlate,
    String model,
    String brand,
    String status
) {}
