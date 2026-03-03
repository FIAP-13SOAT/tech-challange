package com.fiapchallenge.garage.domain.quote;

import java.util.UUID;

public interface QuoteGateway {

    Quote save(Quote quote);

    Quote findByServiceOrderIdOrThrow(UUID serviceOrderId);
}
