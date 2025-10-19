package com.fiapchallenge.garage.unit.customer.util.factory;

import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.application.commands.customer.CreateCustomerCommand;

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

    public static CreateCustomerCommand buildCommand() {
        return new CreateCustomerCommand(NAME, EMAIL, PHONE, CPF_CNPJ.getValue());
    }
}
