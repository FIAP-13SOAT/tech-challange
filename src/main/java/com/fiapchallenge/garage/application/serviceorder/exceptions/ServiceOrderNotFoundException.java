package com.fiapchallenge.garage.application.serviceorder.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class ServiceOrderNotFoundException extends SoatNotFoundException {

    private static final String ERROR_MESSAGE = "Ordem de serviço não encontrada com o id: %s";

    public ServiceOrderNotFoundException(UUID id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
