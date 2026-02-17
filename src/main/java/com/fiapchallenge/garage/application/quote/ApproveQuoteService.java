package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteGateway;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApproveQuoteService implements ApproveQuoteUseCase {

    private final QuoteGateway quoteGateway;
    private final ServiceOrderGateway serviceOrderGateway;

    public ApproveQuoteService(QuoteGateway quoteGateway, ServiceOrderGateway serviceOrderGateway) {
        this.quoteGateway = quoteGateway;
        this.serviceOrderGateway = serviceOrderGateway;
    }

    public Quote handle(UUID serviceOrderId) {
        Quote quote = quoteGateway.findByServiceOrderIdOrThrow(serviceOrderId);
        quote.approve();

        var serviceOrder = serviceOrderGateway.findByIdOrThrow(serviceOrderId);
        serviceOrder.approve();
        serviceOrderGateway.save(serviceOrder);

        quote = quoteGateway.save(quote);

        return quote;
    }
}
