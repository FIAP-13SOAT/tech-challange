package com.fiapchallenge.garage.application.quote.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

import java.util.UUID;

public class QuoteNotFoundException extends SoatNotFoundException {
    public QuoteNotFoundException(UUID serviceOrderId) {
        super("Quote not found for service order: " + serviceOrderId);
    }
}
