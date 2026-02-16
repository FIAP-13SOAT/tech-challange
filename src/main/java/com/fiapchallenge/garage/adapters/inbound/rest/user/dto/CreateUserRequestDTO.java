package com.fiapchallenge.garage.adapters.inbound.rest.user.dto;

import com.fiapchallenge.garage.domain.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CreateUserRequest", description = "Dados para criacao de um usuario")
public record CreateUserRequestDTO(
        @NotNull(message = "Necessario informar o nome completo") String fullname,
        @NotNull(message = "Necessario informar um email") String email,
        @NotNull(message = "Necessario informar uma senha") String password,
        @NotNull(message = "Necessario informar o papel do usuario") UserRole role
) {
}
