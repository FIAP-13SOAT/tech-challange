package com.fiapchallenge.garage.domain.customer.command;

public record CreateCustomerCommand(
        String name,
        String email,
        String phone
) {
}
