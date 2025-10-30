package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.application.quote.command.ApproveQuoteCommand;
import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApproveQuoteService implements ApproveQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final StartServiceOrderExecutionUseCase approveServiceOrderUseCase;

    public ApproveQuoteService(QuoteRepository quoteRepository, StartServiceOrderExecutionUseCase approveServiceOrderUseCase) {
        this.quoteRepository = quoteRepository;
        this.approveServiceOrderUseCase = approveServiceOrderUseCase;
    }

    public Quote handle(ApproveQuoteCommand command) {
        Quote quote = quoteRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Orçamento não encontrado"));

        StartServiceOrderExecutionCommand approveServiceOrderCommand = new StartServiceOrderExecutionCommand(quote.getServiceOrderId());
        approveServiceOrderUseCase.handle(approveServiceOrderCommand);

        return quote;
    }
}
