package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase.CreateCustomerCommand;
import com.fiapchallenge.garage.domain.customer.Customer;

public class CustomerFixture {

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
