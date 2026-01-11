package com.fiapchallenge.garage.domain.serviceorder.exceptions;

public class InvalidStatusToApproveException extends ServiceOrderDomainException {
    public InvalidStatusToApproveException() {
        super("Ordem de serviço deve estar no status aguardando aprovação para aprovar.");
    }
}