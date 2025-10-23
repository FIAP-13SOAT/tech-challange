package com.fiapchallenge.garage.adapters.inbound.controller.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "UpdateCustomer", description = "Dados para atualização de um cliente")
public record UpdateCustomerDTO(
        @NotNull(message = "Necessário informar o nome do cliente") String name,
        @NotNull(message = "Necessário informar o email do cliente") String email,
        @NotNull(message = "Necessário informar o telefone do cliente") String phone
) {
}
