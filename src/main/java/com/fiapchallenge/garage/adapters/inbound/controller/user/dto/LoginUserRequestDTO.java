package com.fiapchallenge.garage.adapters.inbound.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "LoginUserRequest", description = "Login")
public record LoginUserRequestDTO(
        @NotNull(message = "Necessário informar um email") String email,
        @NotNull(message = "Necessário informar uma senha") String password
) {
}
