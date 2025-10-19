package com.fiapchallenge.garage.application.commands.customer;

public record CreateCustomerCommand(
        String name,
        String email,
        String phone,
        String cpfCnpj
) {}
