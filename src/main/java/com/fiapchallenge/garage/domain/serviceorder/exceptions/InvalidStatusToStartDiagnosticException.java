package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class InvalidStatusToStartDiagnosticException extends ServiceOrderDomainException {
    public InvalidStatusToStartDiagnosticException() {
        super("Ordem de serviço deve estar no status recebida para iniciar diagnóstico.");
    }
}