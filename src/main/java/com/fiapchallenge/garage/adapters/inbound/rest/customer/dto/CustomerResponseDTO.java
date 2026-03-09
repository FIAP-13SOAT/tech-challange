package com.fiapchallenge.garage.adapters.inbound.rest.customer.dto;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        String cpfCnpj
) {
}
