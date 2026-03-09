package com.fiapchallenge.garage.adapters.inbound.rest.stockmovement;

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

@Tag(name = "Stock Movements", description = "Operacoes relacionadas ao historico de movimentacoes de estoque")
public interface StockMovementResourceOpenApiSpec {

    @Operation(summary = "Listar todas as movimentacoes", description = "Lista todas as movimentacoes de estoque com paginacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<StockMovementDTO>> listAll(
            @Parameter(description = "Numero da pagina") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da pagina") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Listar movimentacoes por item", description = "Lista movimentacoes de um item especifico do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item nao encontrado")
    })
    ResponseEntity<Page<StockMovementDTO>> listByStockId(
            @Parameter(description = "ID do item") @PathVariable UUID stockId,
            @Parameter(description = "Numero da pagina") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da pagina") @RequestParam(defaultValue = "10") int size);
}
