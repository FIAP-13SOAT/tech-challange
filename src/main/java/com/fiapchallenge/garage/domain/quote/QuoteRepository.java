package com.fiapchallenge.garage.domain.quote;

import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository {

    Quote save(Quote quote);

    Optional<Quote> findById(UUID id);
}
