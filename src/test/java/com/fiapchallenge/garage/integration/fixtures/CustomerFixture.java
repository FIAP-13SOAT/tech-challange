package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.customer.CreateCustomerUseCase;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.application.commands.customer.CreateCustomerCommand;

import java.util.Random;

public class CustomerFixture {
    private static final Random random = new Random();

    public static Customer createCustomer(CreateCustomerUseCase createCustomerUseCase) {

        CreateCustomerCommand command = new CreateCustomerCommand(
            "John Doe",
            "john@example.com",
            "123456789",
            "60850254086"
        );

        return createCustomerUseCase.handle(command);
    }

    public static Customer createCustomer(CreateCustomerUseCase createCustomerUseCase, String name, String email, String phone, String cpfCnpj) {
        CreateCustomerCommand command = new CreateCustomerCommand(name, email, phone, cpfCnpj);
        return createCustomerUseCase.handle(command);
    }
}
