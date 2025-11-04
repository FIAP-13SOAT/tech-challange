package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApproveQuoteService implements ApproveQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final ServiceOrderRepository serviceOrderRepository;

    public ApproveQuoteService(QuoteRepository quoteRepository, ServiceOrderRepository serviceOrderRepository) {
        this.quoteRepository = quoteRepository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    public Quote handle(UUID serviceOrderId) {
        Quote quote = quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId);
        quote.approve();

        var serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);
        serviceOrder.startProgress();
        serviceOrderRepository.save(serviceOrder);

        return quoteRepository.save(quote);
    }
}
