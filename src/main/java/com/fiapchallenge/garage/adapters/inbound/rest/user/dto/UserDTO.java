package com.fiapchallenge.garage.adapters.inbound.rest.user.dto;

import com.fiapchallenge.garage.domain.user.UserRole;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String fullname,
        String email,
        UserRole role
) {
}
