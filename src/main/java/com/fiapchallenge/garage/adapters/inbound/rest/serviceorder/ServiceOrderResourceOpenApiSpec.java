package com.fiapchallenge.garage.adapters.inbound.rest.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.StockItemDTO;
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

@Tag(name = "Ordem de Servico", description = "API para gerenciamento de Ordem de Servico")
public interface ServiceOrderResourceOpenApiSpec {

    @Operation(summary = "Criar uma nova Ordem de Servico", description = "Cria uma nova Ordem de Servico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de servico criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content)
    })
    ResponseEntity<ServiceOrderResponseDTO> create(
            @Parameter(name = "CreateServiceOrder", description = "Dados da Ordem de Servico", schema = @Schema(implementation = CreateServiceOrderDTO.class))
            @Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO);

    @Operation(summary = "Iniciar diagnostico", description = "Inicia diagnostico da Ordem de Servico")
    ResponseEntity<ServiceOrderResponseDTO> startDiagnosis(@PathVariable UUID id);

    @Operation(summary = "Finalizar diagnostico", description = "Finaliza diagnostico da Ordem de Servico")
    ResponseEntity<ServiceOrderResponseDTO> finishDiagnosis(@PathVariable UUID id);

    @Operation(summary = "Iniciar execucao", description = "Inicia execucao da Ordem de Servico")
    ResponseEntity<ServiceOrderResponseDTO> startExecution(@PathVariable UUID id);

    @Operation(summary = "Finalizar ordem de servico", description = "Finalizar execucao da Ordem de Servico")
    ResponseEntity<ServiceOrderResponseDTO> finish(@PathVariable UUID id);

    @Operation(summary = "Entregar servico", description = "Entrega do veiculo para o cliente")
    ResponseEntity<ServiceOrderResponseDTO> deliver(@PathVariable UUID id);

    @Operation(summary = "Cancelar servico", description = "Cancela a Ordem de Servico")
    ResponseEntity<ServiceOrderResponseDTO> setCancelled(@PathVariable UUID id);

    @Operation(summary = "Obter detalhes da ordem de servico", description = "Retorna os detalhes de uma ordem de servico")
    ResponseEntity<ServiceOrderResponseDTO> getServiceOrderDetails(@PathVariable UUID id);

    @Operation(summary = "Adicionar itens de estoque", description = "Adiciona itens de estoque a ordem de servico")
    ResponseEntity<ServiceOrderResponseDTO> addStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems);

    @Operation(summary = "Remover itens de estoque", description = "Remove itens de estoque da ordem de servico")
    ResponseEntity<ServiceOrderResponseDTO> removeStockItems(@PathVariable UUID id, @RequestBody List<StockItemDTO> stockItems);

    @Operation(summary = "Adicionar tipos de servico", description = "Adiciona tipos de servico a ordem de servico")
    ResponseEntity<ServiceOrderResponseDTO> addServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds);

    @Operation(summary = "Remover tipos de servico", description = "Remove tipos de servico da ordem de servico")
    ResponseEntity<ServiceOrderResponseDTO> removeServiceTypes(@PathVariable UUID id, @RequestBody List<UUID> serviceTypeIds);

    @Operation(summary = "Listar ordens ativas", description = "Lista todas as ordens de servico ativas ordenadas por prioridade e data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ordens ativas retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOrderResponseDTO.class)))
    })
    ResponseEntity<List<ServiceOrderResponseDTO>> listActiveOrders();
}
