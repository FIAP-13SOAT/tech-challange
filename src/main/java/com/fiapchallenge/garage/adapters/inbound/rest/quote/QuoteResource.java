package com.fiapchallenge.garage.adapters.inbound.rest.quote;

import com.fiapchallenge.garage.adapters.inbound.controller.quote.dto.QuoteResponseDTO;
import com.fiapchallenge.garage.application.quote.ApproveQuoteUseCase;
import com.fiapchallenge.garage.application.quote.GenerateQuoteUseCase;
import com.fiapchallenge.garage.application.quote.RejectQuoteUseCase;
import com.fiapchallenge.garage.controllers.quote.QuoteController;
import com.fiapchallenge.garage.presenters.quote.QuotePresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/quotes")
public class QuoteResource implements QuoteResourceOpenApiSpec {

    private final QuoteController quoteController;

    public QuoteResource(
            GenerateQuoteUseCase generateQuoteUseCase,
            ApproveQuoteUseCase approveQuoteUseCase,
            RejectQuoteUseCase rejectQuoteUseCase
    ) {
        this.quoteController = new QuoteController(
                generateQuoteUseCase,
                approveQuoteUseCase,
                rejectQuoteUseCase,
                new QuotePresenter()
        );
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    @GetMapping("/service-order/{serviceOrderId}")
    public ResponseEntity<QuoteResponseDTO> generateQuote(@PathVariable UUID serviceOrderId) {
        return ResponseEntity.ok(quoteController.generateQuote(serviceOrderId));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    @PostMapping("/service-order/{serviceOrderId}/approve")
    public ResponseEntity<QuoteResponseDTO> approveQuote(@PathVariable UUID serviceOrderId) {
        return ResponseEntity.ok(quoteController.approveQuote(serviceOrderId));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLERK')")
    @PostMapping("/service-order/{serviceOrderId}/reject")
    public ResponseEntity<QuoteResponseDTO> rejectQuote(@PathVariable UUID serviceOrderId) {
        return ResponseEntity.ok(quoteController.rejectQuote(serviceOrderId));
    }
}
