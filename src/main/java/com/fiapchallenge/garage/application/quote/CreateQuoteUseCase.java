package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.application.quote.command.CreateQuoteCommand;
import com.fiapchallenge.garage.domain.quote.Quote;

public interface CreateQuoteUseCase {

    Quote handle(CreateQuoteCommand command);
}
