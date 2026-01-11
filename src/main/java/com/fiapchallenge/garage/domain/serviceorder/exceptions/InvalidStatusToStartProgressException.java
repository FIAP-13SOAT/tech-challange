package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class InvalidStatusToStartProgressException extends ServiceOrderDomainException {
    public InvalidStatusToStartProgressException() {
        super("Ordem de serviço deve estar no status aguardando execução para iniciar execução.");
    }
}