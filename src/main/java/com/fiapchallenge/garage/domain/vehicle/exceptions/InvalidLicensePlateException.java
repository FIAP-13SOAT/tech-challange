package com.fiapchallenge.garage.domain.vehicle.exceptions;

public class InvalidLicensePlateException extends VehicleDomainException {
    public InvalidLicensePlateException(String licensePlate) {
        super("Invalid license plate format: " + licensePlate);
    }
}
