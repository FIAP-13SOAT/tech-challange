package com.fiapchallenge.garage.domain.vehicle;

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

    public Vehicle() {
        // Default constructor for builder pattern
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String model;
        private String brand;
        private String licensePlate;
        private UUID customerId;
        private String color;
        private Integer year;
        private String observations;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder model(String model) { this.model = model; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        public Builder licensePlate(String licensePlate) { this.licensePlate = licensePlate; return this; }
        public Builder customerId(UUID customerId) { this.customerId = customerId; return this; }
        public Builder color(String color) { this.color = color; return this; }
        public Builder year(Integer year) { this.year = year; return this; }
        public Builder observations(String observations) { this.observations = observations; return this; }

        public Vehicle build() {
            Vehicle vehicle = new Vehicle();
            vehicle.id = this.id;
            vehicle.model = this.model;
            vehicle.brand = this.brand;
            vehicle.licensePlate = this.licensePlate;
            vehicle.customerId = this.customerId;
            vehicle.color = this.color;
            vehicle.year = this.year;
            vehicle.observations = this.observations;
            return vehicle;
        }
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
