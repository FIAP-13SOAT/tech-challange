package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;

import java.util.UUID;

public interface GenerateQuoteUseCase {

    Quote handle(UUID serviceOrderId);
}
