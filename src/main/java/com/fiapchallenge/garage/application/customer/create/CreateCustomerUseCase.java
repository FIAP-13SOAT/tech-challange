package com.fiapchallenge.garage.application.customer.create;

import com.fiapchallenge.garage.domain.customer.Customer;
import jakarta.validation.constraints.NotNull;

public interface CreateCustomerUseCase {

    Customer handle(CreateCustomerCommand command);

    record CreateCustomerCommand(
            @NotNull String name,
            @NotNull String email,
            @NotNull String phone,
            @NotNull String cpfCnpj
    ) {}
}
