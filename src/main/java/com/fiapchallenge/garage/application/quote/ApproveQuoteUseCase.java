package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;

import java.util.UUID;

public interface ApproveQuoteUseCase {

    Quote handle(UUID serviceOrderId);
}
