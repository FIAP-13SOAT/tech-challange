package com.fiapchallenge.garage.application.customer.delete;

import java.util.UUID;

public interface DeleteCustomerUseCase {

    void handle(DeleteCustomerCmd command);

    public record DeleteCustomerCmd(UUID id) {}
}
