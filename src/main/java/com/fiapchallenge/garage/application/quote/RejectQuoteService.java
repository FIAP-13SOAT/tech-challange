package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RejectQuoteService implements RejectQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final ServiceOrderGateway serviceOrderGateway;

    public RejectQuoteService(QuoteRepository quoteRepository, ServiceOrderGateway serviceOrderGateway) {
        this.quoteRepository = quoteRepository;
        this.serviceOrderGateway = serviceOrderGateway;
    }

    public Quote handle(UUID serviceOrderId) {
        Quote quote = quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId);
        quote.reject();

        var serviceOrder = serviceOrderGateway.findByIdOrThrow(serviceOrderId);
        serviceOrder.cancel();
        serviceOrderGateway.save(serviceOrder);

        return quoteRepository.save(quote);
    }
}
