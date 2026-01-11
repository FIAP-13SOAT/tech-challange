package com.fiapchallenge.garage.domain.quote.exceptions;

import com.fiapchallenge.garage.domain.quote.QuoteStatus;

public class CannotApproveQuoteException extends QuoteDomainException {
    public CannotApproveQuoteException(QuoteStatus currentStatus) {
        super("Cannot approve quote with status: " + currentStatus);
    }
}
