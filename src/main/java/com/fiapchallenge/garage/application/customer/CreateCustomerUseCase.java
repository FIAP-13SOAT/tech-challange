package com.fiapchallenge.garage.application.customer;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.application.commands.customer.CreateCustomerCommand;

public interface CreateCustomerUseCase {

    Customer handle(CreateCustomerCommand command);
}
