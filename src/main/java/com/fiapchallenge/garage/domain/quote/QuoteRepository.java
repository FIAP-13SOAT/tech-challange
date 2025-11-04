package com.fiapchallenge.garage.domain.quote;

import java.util.UUID;

public interface QuoteRepository {

    Quote save(Quote quote);
    Quote findByServiceOrderIdOrThrow(UUID serviceOrderId);
}
