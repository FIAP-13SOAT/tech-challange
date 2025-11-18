package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ordem de Serviço", description = "API para gerenciamento de Ordem de Serviço")
public interface ServiceOrderControllerOpenApiSpec {

    @Operation(summary = "Criar uma nova Ordem de Serviço", description = "Cria uma nova Ordem de Serviço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordem de serviço criada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOrder.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<ServiceOrder> create(
        @Parameter(name = "CreateServiceOrder", description = "Dados da Ordem de Serviço", schema = @Schema(implementation = CreateServiceOrderDTO.class))
        @Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO);

    @Operation(summary = "Iniciar diagnóstico", description = "Inicia diagnóstico da Ordem de Serviço")
    ResponseEntity<ServiceOrder> startDiagnosis(@PathVariable UUID id);

    @Operation(summary = "Finalizar diagnóstico", description = "Finaliza diagnóstico da Ordem de Serviço")
    ResponseEntity<ServiceOrder> finishDiagnosis(@PathVariable UUID id);

    @Operation(summary = "Finalizar ordem de serviço", description = "Finalizar execução da Ordem de Serviço")
    ResponseEntity<ServiceOrder> finish(@PathVariable UUID id);

    @Operation(summary = "Entregar serviço", description = "Entrega do veículo para o cliente")
    ResponseEntity<ServiceOrder> deliver(@PathVariable UUID id);

    @Operation(summary = "Cancelar serviço", description = "Cancela a Ordem de Serviço")
    ResponseEntity<ServiceOrder> setCancelled(@PathVariable UUID id);

    @Operation(summary = "Obter detalhes da ordem de serviço", description = "Retorna os detalhes de uma ordem de serviço")
    ResponseEntity<ServiceOrder> getServiceOrderDetails(@PathVariable UUID id);

    @Operation(summary = "Adicionar itens de estoque", description = "Adiciona itens de estoque à ordem de serviço")
    ResponseEntity<ServiceOrder> addStockItems(@PathVariable UUID id, @RequestBody List<com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO> stockItems);

    @Operation(summary = "Remover itens de estoque", description = "Remove itens de estoque da ordem de serviço")
    ResponseEntity<ServiceOrder> removeStockItems(@PathVariable UUID id, @RequestBody List<com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO> stockItems);

    @Operation(summary = "Adicionar tipos de serviço", description = "Adiciona tipos de serviço à ordem de serviço")
    ResponseEntity<ServiceOrder> addServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds);

    @Operation(summary = "Remover tipos de serviço", description = "Remove tipos de serviço da ordem de serviço")
    ResponseEntity<ServiceOrder> removeServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds);

    @Operation(summary = "Listar ordens ativas", description = "Lista todas as ordens de serviço ativas ordenadas por prioridade e data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ordens ativas retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOrder.class)))
    })
    ResponseEntity<List<ServiceOrder>> listActiveOrders();
}
