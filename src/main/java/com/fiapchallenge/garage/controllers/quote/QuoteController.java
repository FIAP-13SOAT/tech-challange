package com.fiapchallenge.garage.controllers.quote;

import com.fiapchallenge.garage.adapters.inbound.controller.quote.dto.QuoteResponseDTO;
import com.fiapchallenge.garage.application.quote.ApproveQuoteUseCase;
import com.fiapchallenge.garage.application.quote.GenerateQuoteUseCase;
import com.fiapchallenge.garage.application.quote.RejectQuoteUseCase;
import com.fiapchallenge.garage.presenters.quote.QuotePresenter;

import java.util.UUID;

public class QuoteController {

    private final GenerateQuoteUseCase generateQuoteUseCase;
    private final ApproveQuoteUseCase approveQuoteUseCase;
    private final RejectQuoteUseCase rejectQuoteUseCase;
    private final QuotePresenter quotePresenter;

    public QuoteController(
            GenerateQuoteUseCase generateQuoteUseCase,
            ApproveQuoteUseCase approveQuoteUseCase,
            RejectQuoteUseCase rejectQuoteUseCase,
            QuotePresenter quotePresenter
    ) {
        this.generateQuoteUseCase = generateQuoteUseCase;
        this.approveQuoteUseCase = approveQuoteUseCase;
        this.rejectQuoteUseCase = rejectQuoteUseCase;
        this.quotePresenter = quotePresenter;
    }

    public QuoteResponseDTO generateQuote(UUID serviceOrderId) {
        return quotePresenter.present(generateQuoteUseCase.handle(serviceOrderId));
    }

    public QuoteResponseDTO approveQuote(UUID serviceOrderId) {
        return quotePresenter.present(approveQuoteUseCase.handle(serviceOrderId));
    }

    public QuoteResponseDTO rejectQuote(UUID serviceOrderId) {
        return quotePresenter.present(rejectQuoteUseCase.handle(serviceOrderId));
    }
}
