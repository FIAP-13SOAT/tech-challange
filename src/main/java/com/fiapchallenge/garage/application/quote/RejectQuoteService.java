package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RejectQuoteService implements RejectQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final ServiceOrderRepository serviceOrderRepository;

    public RejectQuoteService(QuoteRepository quoteRepository, ServiceOrderRepository serviceOrderRepository) {
        this.quoteRepository = quoteRepository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    public Quote handle(UUID serviceOrderId) {
        Quote quote = quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId);
        quote.reject();

        var serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);
        serviceOrder.cancel();
        serviceOrderRepository.save(serviceOrder);

        return quoteRepository.save(quote);
    }
}
