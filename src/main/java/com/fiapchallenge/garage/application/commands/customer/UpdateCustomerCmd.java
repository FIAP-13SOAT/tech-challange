package com.fiapchallenge.garage.application.commands.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "UpdateCustomer", description = "Comando para atualizar um cliente")
public record UpdateCustomerCmd(
        @NotNull(message = "Necessário informar o nome do cliente") String name,
        @NotNull(message = "Necessário informar o email do cliente") String email,
        @NotNull(message = "Necessário informar o telefone do cliente") String phone
) {
}