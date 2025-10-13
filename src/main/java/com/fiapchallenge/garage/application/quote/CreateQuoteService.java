package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.application.quote.command.CreateQuoteCommand;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CreateQuoteService implements CreateQuoteUseCase {

    private final QuoteRepository quoteRepository;

    public CreateQuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository  = quoteRepository;
    }

    @Override
    public Quote handle(CreateQuoteCommand command) {
        ServiceOrder serviceOrder = command.serviceOrder();
        Quote quote = new Quote(null, serviceOrder.getId(), calculateValue(serviceOrder));
        return quoteRepository.save(quote);
    }

    private BigDecimal calculateValue(ServiceOrder serviceOrder) {
        BigDecimal value = BigDecimal.ZERO;
        for (var serviceType : serviceOrder.getServiceTypeList()) {
            value = value.add(serviceType.getValue());
        }

        return value;
    }
}
