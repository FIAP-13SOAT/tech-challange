package com.fiapchallenge.garage.domain.quote.exceptions;

import com.fiapchallenge.garage.domain.quote.QuoteStatus;

public class InvalidQuoteStatusException extends QuoteDomainException {
    public InvalidQuoteStatusException(QuoteStatus currentStatus, String operation) {
        super(String.format("Cannot %s quote with status %s", operation, currentStatus));
    }
}
