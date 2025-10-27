package com.fiapchallenge.garage.application.customer.update;

import com.fiapchallenge.garage.domain.customer.Customer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public interface UpdateCustomerUseCase {

    Customer handle(UpdateCustomerCmd command);

    @Schema(name = "UpdateCustomer", description = "Comando para atualizar um cliente")
    record UpdateCustomerCmd(
       UUID id,
       String name,
       String email,
       String phone
    ) {}
}
