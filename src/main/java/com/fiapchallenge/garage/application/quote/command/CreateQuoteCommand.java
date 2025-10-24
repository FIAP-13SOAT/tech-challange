package com.fiapchallenge.garage.application.quote.command;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

public record CreateQuoteCommand(
        ServiceOrder serviceOrder
) {
}
