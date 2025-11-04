package com.fiapchallenge.garage.adapters.inbound.controller.quote;

import com.fiapchallenge.garage.application.quote.*;
import com.fiapchallenge.garage.domain.quote.Quote;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/quotes")
@Tag(name = "Quote", description = "Quote management API")
public class QuoteController {
    private final GenerateQuoteUseCase generateQuoteUseCase;
    private final ApproveQuoteUseCase approveQuoteUseCase;
    private final RejectQuoteUseCase rejectQuoteUseCase;

    public QuoteController(GenerateQuoteUseCase generateQuoteService, ApproveQuoteUseCase approveQuoteUseCase, RejectQuoteUseCase rejectQuoteUseCase) {
        this.generateQuoteUseCase = generateQuoteService;
        this.approveQuoteUseCase = approveQuoteUseCase;
        this.rejectQuoteUseCase = rejectQuoteUseCase;
    }

    @Operation(summary = "Gerar orçamento", description = "Gera orçamento detalhado para uma ordem de serviço")
    @GetMapping("/service-order/{serviceOrderId}")
    public ResponseEntity<Quote> generateQuote(@PathVariable UUID serviceOrderId) {
        Quote quote = generateQuoteUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(quote);
    }

    @Operation(summary = "Aprovar orçamento", description = "Aprova o orçamento de uma ordem de serviço")
    @PostMapping("/service-order/{serviceOrderId}/approve")
    public ResponseEntity<Quote> approveQuote(@PathVariable UUID serviceOrderId) {
        Quote quote = approveQuoteUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(quote);
    }

    @Operation(summary = "Rejeitar orçamento", description = "Rejeita o orçamento de uma ordem de serviço")
    @PostMapping("/service-order/{serviceOrderId}/reject")
    public ResponseEntity<Quote> rejectQuote(@PathVariable UUID serviceOrderId) {
        Quote quote = rejectQuoteUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(quote);
    }
}
