package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class InvalidStatusToCancelException extends ServiceOrderDomainException {
    public InvalidStatusToCancelException() {
        super("Não é possível cancelar uma ordem de serviço que já esteja completada, entregue ou cancelada.");
    }
}