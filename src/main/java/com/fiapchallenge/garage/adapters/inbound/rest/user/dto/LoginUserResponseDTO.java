package com.fiapchallenge.garage.adapters.inbound.rest.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "LoginUserResponse", description = "Login")
public record LoginUserResponseDTO(
        String token,
        Instant expiration
) {
}
