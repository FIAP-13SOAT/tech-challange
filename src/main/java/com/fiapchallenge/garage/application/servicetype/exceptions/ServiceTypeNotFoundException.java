package com.fiapchallenge.garage.application.servicetype.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class ServiceTypeNotFoundException extends SoatNotFoundException {
    public ServiceTypeNotFoundException(UUID serviceTypeId) {
        super("Tipo de serviço não encontrado com id: " + serviceTypeId);
    }
}