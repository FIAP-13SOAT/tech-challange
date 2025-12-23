package com.fiapchallenge.garage.adapters.inbound.controller.quote.dto;

import com.fiapchallenge.garage.domain.quote.QuoteStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record QuoteResponseDTO(
        UUID id,
        UUID serviceOrderId,
        BigDecimal totalAmount,
        QuoteStatus status,
        LocalDateTime createdAt
) {
}