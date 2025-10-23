package com.fiapchallenge.garage.domain.vehicle;

import com.fiapchallenge.garage.domain.vehicle.command.CreateVehicleCommand;

import java.util.UUID;

public class Vehicle {

    UUID id;
    String model;
    String brand;
    String licensePlate;
    UUID customerId;
    String color;
    Integer year;
    String observations;

    public Vehicle(CreateVehicleCommand command) {
        this.model = command.model();
        this.brand = command.brand();
        this.licensePlate = command.licensePlate();
        this.customerId = command.customerId();
        this.color = command.color();
        this.year = command.year();
        this.observations = command.observations();
    }

    public Vehicle(
            UUID id,
            String model,
            String brand,
            String licensePlate,
            UUID customerId,
            String color,
            Integer year,
            String observations
    ) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.customerId = customerId;
        this.color = color;
        this.year = year;
        this.observations = observations;
    }

    public UUID getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getColor() {
        return color;
    }

    public Integer getYear() {
        return year;
    }

    public String getObservations() {
        return observations;
    }
}
