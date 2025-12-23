package com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceTypeDTO(
        UUID id,
        String description,
        BigDecimal value
) {
}