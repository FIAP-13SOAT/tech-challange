package com.fiapchallenge.garage.unit.quote.util.factory;

import com.fiapchallenge.garage.domain.quote.Quote;

import java.math.BigDecimal;
import java.util.UUID;

public class QuoteTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final BigDecimal VALUE = new BigDecimal("150.00");

    public static Quote createQuote(UUID serviceOrderId, UUID customerId) {
        return new Quote(ID, customerId, serviceOrderId, VALUE);
    }
}
