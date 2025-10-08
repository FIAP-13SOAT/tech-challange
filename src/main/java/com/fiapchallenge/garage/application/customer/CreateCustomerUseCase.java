package com.fiapchallenge.garage.application.customer;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;

public interface CreateCustomerUseCase {

    Customer handle(CreateCustomerCommand command);
}
