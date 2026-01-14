package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class InvalidStatusToDeliverException extends ServiceOrderDomainException {
    public InvalidStatusToDeliverException() {
        super("Ordem de serviço deve estar no status concluída para entregar.");
    }
}