package com.fiapchallenge.garage.adapters.inbound.controller.quote;

import com.fiapchallenge.garage.application.quote.*;
import com.fiapchallenge.garage.domain.quote.Quote;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/quotes")
public class QuoteController implements QuoteControllerOpenApiSpec {

    private final GenerateQuoteUseCase generateQuoteUseCase;
    private final ApproveQuoteUseCase approveQuoteUseCase;
    private final RejectQuoteUseCase rejectQuoteUseCase;

    public QuoteController(GenerateQuoteUseCase generateQuoteService,
                           ApproveQuoteUseCase approveQuoteUseCase,
                           RejectQuoteUseCase rejectQuoteUseCase) {

        this.generateQuoteUseCase = generateQuoteService;
        this.approveQuoteUseCase = approveQuoteUseCase;
        this.rejectQuoteUseCase = rejectQuoteUseCase;
    }

    @Override
    @GetMapping("/service-order/{serviceOrderId}")
    public ResponseEntity<Quote> generateQuote(@PathVariable UUID serviceOrderId) {
        Quote quote = generateQuoteUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(quote);
    }

    @Override
    @PostMapping("/service-order/{serviceOrderId}/approve")
    public ResponseEntity<Quote> approveQuote(@PathVariable UUID serviceOrderId) {
        Quote quote = approveQuoteUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(quote);
    }

    @Override
    @PostMapping("/service-order/{serviceOrderId}/reject")
    public ResponseEntity<Quote> rejectQuote(@PathVariable UUID serviceOrderId) {
        Quote quote = rejectQuoteUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(quote);
    }
}
