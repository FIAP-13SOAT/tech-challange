package com.fiapchallenge.garage.adapters.inbound.rest.stock;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.AddStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.ConsumeStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.CreateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.StockDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.UpdateStockRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Stock", description = "Operacoes relacionadas ao estoque")
public interface StockResourceOpenApiSpec {

    @Operation(summary = "Criar item de estoque", description = "Cria um novo item no estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    ResponseEntity<StockDTO> create(@Valid @RequestBody CreateStockRequestDTO createStockDTO);

    @Operation(summary = "Listar itens do estoque", description = "Lista todos os itens do estoque com paginacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<StockDTO>> list(
            @Parameter(description = "Numero da pagina") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da pagina") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Atualizar item do estoque", description = "Atualiza um item existente no estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item nao encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    ResponseEntity<StockDTO> update(@Parameter(description = "ID do item") @PathVariable UUID id, @Valid @RequestBody UpdateStockRequestDTO updateStockDTO);

    @Operation(summary = "Deletar item do estoque", description = "Remove um item do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item nao encontrado")
    })
    ResponseEntity<Void> delete(@Parameter(description = "ID do item") @PathVariable UUID id);

    @Operation(summary = "Consumir estoque", description = "Consome uma quantidade especifica de um item do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque consumido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quantidade invalida ou estoque insuficiente"),
            @ApiResponse(responseCode = "404", description = "Item nao encontrado")
    })
    ResponseEntity<StockDTO> consumeStock(
            @Parameter(description = "ID do item") @PathVariable UUID id,
            @Valid @RequestBody ConsumeStockRequestDTO request);

    @Operation(summary = "Adicionar estoque", description = "Adiciona uma quantidade especifica a um item do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quantidade invalida"),
            @ApiResponse(responseCode = "404", description = "Item nao encontrado")
    })
    ResponseEntity<StockDTO> addStock(
            @Parameter(description = "ID do item") @PathVariable UUID id,
            @Valid @RequestBody AddStockRequestDTO request);
}
