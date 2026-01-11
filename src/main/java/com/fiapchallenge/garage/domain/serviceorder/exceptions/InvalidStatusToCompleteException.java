package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class InvalidStatusToCompleteException extends ServiceOrderDomainException {
    public InvalidStatusToCompleteException() {
        super("Ordem de serviço deve estar no status em execução para concluir.");
    }
}