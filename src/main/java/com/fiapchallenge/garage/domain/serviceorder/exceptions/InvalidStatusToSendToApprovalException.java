package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class InvalidStatusToSendToApprovalException extends ServiceOrderDomainException {
    public InvalidStatusToSendToApprovalException() {
        super("Ordem de serviço deve estar no status em diagnóstico para finalizar diagnóstico.");
    }
}