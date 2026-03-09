package com.fiapchallenge.garage.presenters.quote;

import com.fiapchallenge.garage.adapters.inbound.controller.quote.dto.QuoteResponseDTO;
import com.fiapchallenge.garage.domain.quote.Quote;

public class QuotePresenter {

    public QuoteResponseDTO present(Quote quote) {
        return new QuoteResponseDTO(
                quote.getId(),
                quote.getServiceOrderId(),
                quote.getTotalAmount(),
                quote.getStatus(),
                quote.getCreatedAt()
        );
    }
}
