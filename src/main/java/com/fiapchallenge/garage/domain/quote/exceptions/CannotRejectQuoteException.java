package com.fiapchallenge.garage.domain.quote.exceptions;

import com.fiapchallenge.garage.domain.quote.QuoteStatus;

public class CannotRejectQuoteException extends QuoteDomainException {
    public CannotRejectQuoteException(QuoteStatus currentStatus) {
        super("Cannot reject quote with status: " + currentStatus);
    }
}
