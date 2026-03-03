package com.fiapchallenge.garage.adapters.inbound.rest.quote;

import com.fiapchallenge.garage.adapters.inbound.controller.quote.dto.QuoteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Orcamento", description = "API para gerenciamento de orcamentos")
public interface QuoteResourceOpenApiSpec {

    @Operation(summary = "Gerar orcamento", description = "Gera orcamento detalhado para uma ordem de servico")
    ResponseEntity<QuoteResponseDTO> generateQuote(
            @Parameter(name = "serviceOrderId", description = "ID da ordem de servico") @PathVariable UUID serviceOrderId
    );

    @Operation(summary = "Aprovar orcamento", description = "Aprova o orcamento de uma ordem de servico")
    ResponseEntity<QuoteResponseDTO> approveQuote(
            @Parameter(name = "serviceOrderId", description = "ID da ordem de servico") @PathVariable UUID serviceOrderId
    );

    @Operation(summary = "Rejeitar orcamento", description = "Rejeita o orcamento de uma ordem de servico")
    ResponseEntity<QuoteResponseDTO> rejectQuote(
            @Parameter(name = "serviceOrderId", description = "ID da ordem de servico") @PathVariable UUID serviceOrderId
    );
}
