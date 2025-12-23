package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorderexecution.StartServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApproveQuoteService implements ApproveQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final ServiceOrderRepository serviceOrderRepository;
    private final StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase;

    public ApproveQuoteService(QuoteRepository quoteRepository, ServiceOrderRepository serviceOrderRepository, StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase) {
        this.quoteRepository = quoteRepository;
        this.serviceOrderRepository = serviceOrderRepository;
        this.startServiceOrderExecutionUseCase = startServiceOrderExecutionUseCase;
    }

    public Quote handle(UUID serviceOrderId) {
        Quote quote = quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId);
        quote.approve();

        var serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);
        serviceOrder.approve();
        serviceOrderRepository.save(serviceOrder);

        quote = quoteRepository.save(quote);

        return quote;
    }
}
