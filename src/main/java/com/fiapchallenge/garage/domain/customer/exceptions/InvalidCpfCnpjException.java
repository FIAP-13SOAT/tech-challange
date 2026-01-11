package com.fiapchallenge.garage.domain.customer.exceptions;

public class InvalidCpfCnpjException extends CustomerDomainException {
    public InvalidCpfCnpjException(String value) {
        super("CPF ou CNPJ inválido: " + value);
    }
}