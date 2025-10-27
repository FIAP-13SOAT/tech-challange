package com.fiapchallenge.garage.unit.customer.util.factory;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;
import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;

import java.util.UUID;

public class CustomerTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final String NAME = "Pix JR";
    public static final String EMAIL = "pixjr@gmail.com";
    public static final String PHONE = "+5547999999999";
    public static final CpfCnpj CPF_CNPJ = new CpfCnpj("59331609035");

    public static Customer build() {
        return new Customer(ID, NAME, EMAIL, PHONE, CPF_CNPJ);
    }

    public static CreateCustomerUseCase.CreateCustomerCommand buildCommand() {
        return new CreateCustomerUseCase.CreateCustomerCommand(NAME, EMAIL, PHONE, CPF_CNPJ.getValue());
    }
}
