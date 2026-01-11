package com.fiapchallenge.garage.application.customer.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatValidationException;

public class DuplicateCpfCnpjException extends SoatValidationException {
    public DuplicateCpfCnpjException() {
        super("CPF/CNPJ já cadastrado");
    }
}