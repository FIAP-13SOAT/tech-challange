package com.fiapchallenge.garage.adapters.inbound.rest.serviceorderexecution;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Service Order Execution", description = "API para execucao de Ordem de Servico")
public interface ServiceOrderExecutionResourceOpenApiSpec {

    @Operation(summary = "Iniciar execucao", description = "Inicia execucao da Ordem de Servico")
    ResponseEntity<ServiceOrderResponseDTO> startExecution(
            @Parameter(name = "id", description = "ID da ordem de servico") @PathVariable UUID id
    );
}
