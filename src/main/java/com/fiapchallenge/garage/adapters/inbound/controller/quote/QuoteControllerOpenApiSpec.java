package com.fiapchallenge.garage.adapters.inbound.controller.quote;

import com.fiapchallenge.garage.adapters.inbound.controller.quote.dto.QuoteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Orçamento", description = "API para gerenciamento de orçamentos")
public interface QuoteControllerOpenApiSpec {

    @Operation(summary = "Gerar orçamento", description = "Gera orçamento detalhado para uma ordem de serviço")
    ResponseEntity<QuoteResponseDTO> generateQuote(@PathVariable UUID serviceOrderId);

    @Operation(summary = "Aprovar orçamento", description = "Aprova o orçamento de uma ordem de serviço")
    ResponseEntity<QuoteResponseDTO> approveQuote(@PathVariable UUID serviceOrderId);

    @Operation(summary = "Rejeitar orçamento", description = "Rejeita o orçamento de uma ordem de serviço")
    ResponseEntity<QuoteResponseDTO> rejectQuote(@PathVariable UUID serviceOrderId);
}