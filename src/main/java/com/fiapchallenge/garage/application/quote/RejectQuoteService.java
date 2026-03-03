package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteGateway;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RejectQuoteService implements RejectQuoteUseCase {

    private final QuoteGateway quoteGateway;
    private final ServiceOrderGateway serviceOrderGateway;

    public RejectQuoteService(QuoteGateway quoteGateway, ServiceOrderGateway serviceOrderGateway) {
        this.quoteGateway = quoteGateway;
        this.serviceOrderGateway = serviceOrderGateway;
    }

    public Quote handle(UUID serviceOrderId) {
        Quote quote = quoteGateway.findByServiceOrderIdOrThrow(serviceOrderId);
        quote.reject();

        var serviceOrder = serviceOrderGateway.findByIdOrThrow(serviceOrderId);
        serviceOrder.cancel();
        serviceOrderGateway.save(serviceOrder);

        return quoteGateway.save(quote);
    }
}
