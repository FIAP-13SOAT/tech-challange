package com.fiapchallenge.garage.adapters.inbound.controller.stock;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.CreateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.StockDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.UpdateStockRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Stock", description = "Operações relacionadas ao estoque")
public interface StockControllerOpenApiSpec {

    @Operation(summary = "Criar item de estoque", description = "Cria um novo item no estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<StockDTO> create(@Valid @RequestBody CreateStockRequestDTO createStockDTO);

    @Operation(summary = "Listar itens do estoque", description = "Lista todos os itens do estoque com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<StockDTO>> list(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Atualizar item do estoque", description = "Atualiza um item existente no estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<StockDTO> update(@Parameter(description = "ID do item") @PathVariable UUID id, @Valid @RequestBody UpdateStockRequestDTO updateStockDTO);

    @Operation(summary = "Deletar item do estoque", description = "Remove um item do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    ResponseEntity<Void> delete(@Parameter(description = "ID do item") @PathVariable UUID id);

    @Operation(summary = "Consumir estoque", description = "Consome uma quantidade específica de um item do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque consumido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quantidade inválida ou estoque insuficiente"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    ResponseEntity<StockDTO> consumeStock(
            @Parameter(description = "ID do item") @PathVariable UUID id,
            @Parameter(description = "Quantidade a consumir") @RequestParam @Positive(message = "Quantidade deve ser positiva") Integer quantity);

    @Operation(summary = "Adicionar estoque", description = "Adiciona uma quantidade específica a um item do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quantidade inválida"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    ResponseEntity<StockDTO> addStock(
            @Parameter(description = "ID do item") @PathVariable UUID id,
            @Parameter(description = "Quantidade a adicionar") @RequestParam @Positive(message = "Quantidade deve ser positiva") Integer quantity);
}