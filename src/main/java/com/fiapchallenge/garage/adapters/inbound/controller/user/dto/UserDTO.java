package com.fiapchallenge.garage.adapters.inbound.controller.user.dto;

import com.fiapchallenge.garage.domain.user.UserRole;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String fullname,
        String email,
        UserRole role
) {
}