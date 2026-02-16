package com.fiapchallenge.garage.adapters.inbound.rest.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "LoginUserRequest", description = "Login")
public record LoginUserRequestDTO(
        @NotNull(message = "Necessario informar um email") String email,
        @NotNull(message = "Necessario informar uma senha") String password
) {
}
