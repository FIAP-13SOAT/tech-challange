package com.fiapchallenge.garage.adapters.inbound.controller.quote.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.quote.dto.QuoteResponseDTO;
import com.fiapchallenge.garage.domain.quote.Quote;

public class QuoteMapper {

    public static QuoteResponseDTO toResponseDTO(Quote quote) {
        return new QuoteResponseDTO(
                quote.getId(),
                quote.getServiceOrderId(),
                quote.getTotalAmount(),
                quote.getStatus(),
                quote.getCreatedAt()
        );
    }
}