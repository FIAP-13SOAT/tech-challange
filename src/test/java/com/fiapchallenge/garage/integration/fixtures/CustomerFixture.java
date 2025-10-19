package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.customer.CreateCustomerService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;

public class CustomerFixture {

    public static Customer createCustomer(CreateCustomerService createCustomerService) {
        CreateCustomerCommand command = new CreateCustomerCommand(
                "John Doe",
                "john@example.com",
                "123456789"
        );
        return createCustomerService.handle(command);
    }
}
