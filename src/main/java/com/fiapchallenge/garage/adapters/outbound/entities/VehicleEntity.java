package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "vehicle")
@Entity
public class VehicleEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String model;
    private String brand;

    @Column(unique = true)
    private String licensePlate;

    private UUID customerId;
    private String color;
    private Integer year;
    private String observations;

    public VehicleEntity() {
    }

    public VehicleEntity(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.model = vehicle.getModel();
        this.brand = vehicle.getBrand();
        this.licensePlate = vehicle.getLicensePlate();
        this.customerId = vehicle.getCustomerId();
        this.color = vehicle.getColor();
        this.year = vehicle.getYear();
        this.observations = vehicle.getObservations();
    }

    public UUID getId() {
        return id;
    }

    public VehicleEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getModel() {
        return model;
    }

    public VehicleEntity setModel(String model) {
        this.model = model;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public VehicleEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleEntity setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public VehicleEntity setCustomerId(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getColor() {
        return color;
    }

    public VehicleEntity setColor(String color) {
        this.color = color;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public VehicleEntity setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getObservations() {
        return observations;
    }

    public VehicleEntity setObservations(String observations) {
        this.observations = observations;
        return this;
    }
}
