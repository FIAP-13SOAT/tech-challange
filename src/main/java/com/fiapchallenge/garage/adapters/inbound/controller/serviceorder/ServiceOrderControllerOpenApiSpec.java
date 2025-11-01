package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.CreateServiceOrderDTO;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "ServiceOrder", description = "ServiceOrder management API")
public interface ServiceOrderControllerOpenApiSpec {

    @Operation(summary = "Criar uma nova Ordem de Serviço", description = "Cria uma nova Ordem de Serviço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordem de serviço criada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOrder.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping(consumes = "application/json")
    ResponseEntity<ServiceOrder> create(
        @Parameter(name = "CreateServiceOrder", description = "Dados da Ordem de Serviço", schema = @Schema(implementation = CreateServiceOrderDTO.class))
        @Valid @RequestBody CreateServiceOrderDTO createServiceOrderDTO);

    @Operation(summary = "Iniciar diagnóstico", description = "Muda status para IN_DIAGNOSIS")
    @PostMapping("/{id}/in-diagnosis")
    ResponseEntity<ServiceOrder> setInDiagnosis(@PathVariable UUID id);

    @Operation(summary = "Aguardar aprovação", description = "Muda status para AWAITING_APPROVAL")
    @PostMapping("/{id}/awaiting-approval")
    ResponseEntity<ServiceOrder> setAwaitingApproval(@PathVariable UUID id);

    @Operation(summary = "Iniciar progresso", description = "Muda status para IN_PROGRESS")
    @PostMapping("/{id}/in-progress")
    ResponseEntity<ServiceOrder> setInProgress(@PathVariable UUID id);

    @Operation(summary = "Completar serviço", description = "Muda status para COMPLETED")
    @PostMapping("/{id}/completed")
    ResponseEntity<ServiceOrder> setCompleted(@PathVariable UUID id);

    @Operation(summary = "Entregar serviço", description = "Muda status para DELIVERED")
    @PostMapping("/{id}/delivered")
    ResponseEntity<ServiceOrder> setDelivered(@PathVariable UUID id);

    @Operation(summary = "Cancelar serviço", description = "Muda status para CANCELLED")
    @PostMapping("/{id}/cancelled")
    ResponseEntity<ServiceOrder> setCancelled(@PathVariable UUID id);

    @Operation(summary = "Listar status", description = "Lista todos os status possíveis")
    @GetMapping("/status")
    ResponseEntity<List<ServiceOrderStatus>> getAllStatus();
}
