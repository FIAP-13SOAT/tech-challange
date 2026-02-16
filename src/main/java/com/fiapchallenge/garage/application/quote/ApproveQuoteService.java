package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApproveQuoteService implements ApproveQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final ServiceOrderGateway serviceOrderGateway;

    public ApproveQuoteService(QuoteRepository quoteRepository, ServiceOrderGateway serviceOrderGateway) {
        this.quoteRepository = quoteRepository;
        this.serviceOrderGateway = serviceOrderGateway;
    }

    public Quote handle(UUID serviceOrderId) {
        Quote quote = quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId);
        quote.approve();

        var serviceOrder = serviceOrderGateway.findByIdOrThrow(serviceOrderId);
        serviceOrder.approve();
        serviceOrderGateway.save(serviceOrder);

        quote = quoteRepository.save(quote);

        return quote;
    }
}
