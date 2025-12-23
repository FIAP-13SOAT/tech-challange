package com.fiapchallenge.garage.adapters.inbound.controller.stockmovement;

import com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.dto.StockMovementDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Stock Movements", description = "Operações relacionadas ao histórico de movimentações de estoque")
public interface StockMovementControllerOpenApiSpec {

    @Operation(summary = "Listar todas as movimentações", description = "Lista todas as movimentações de estoque com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<StockMovementDTO>> listAll(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Listar movimentações por item", description = "Lista movimentações de um item específico do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    ResponseEntity<Page<StockMovementDTO>> listByStockId(
            @Parameter(description = "ID do item") @PathVariable UUID stockId,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size);
}