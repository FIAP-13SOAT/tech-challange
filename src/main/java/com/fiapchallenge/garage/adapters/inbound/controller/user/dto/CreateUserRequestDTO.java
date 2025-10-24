package com.fiapchallenge.garage.adapters.inbound.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CreateUserRequest", description = "Dados para criação de um Usuário")
public record CreateUserRequestDTO(
        @NotNull(message = "Necessário informar o nome completo") String fullname,
        @NotNull(message = "Necessário informar um email") String email,
        @NotNull(message = "Necessário informar uma senha") String password
) {
}
