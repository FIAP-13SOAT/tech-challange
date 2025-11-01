package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.application.quote.command.ApproveQuoteCommand;
import com.fiapchallenge.garage.domain.quote.Quote;

public interface ApproveQuoteUseCase {

    Quote handle(ApproveQuoteCommand command);
}
