package com.fiapchallenge.garage.adapters.inbound.rest.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CustomerRequest", description = "Dados para criacao de um cliente")
public record CreateCustomerRequestDTO(
        @NotNull(message = "Necessario informar o nome do cliente") String name,
        @NotNull(message = "Necessario informar o email do cliente") String email,
        @NotNull(message = "Necessario informar o telefone do cliente") String phone,
        @NotNull(message = "Necessario informar o CPF/CNPJ do cliente") String cpfCnpj
) {
}
