package com.fiapchallenge.garage.adapters.inbound.controller.user.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDTO(
        @NotNull(message = "Necessário informar o nome completo") String fullname,
        @NotNull(message = "Necessário informar um email") String email,
        @NotNull(message = "Necessário informar uma senha") String password
) {
}
