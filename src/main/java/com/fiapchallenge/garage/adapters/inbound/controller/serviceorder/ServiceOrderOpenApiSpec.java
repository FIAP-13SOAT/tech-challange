package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "ServiceOrder", description = "ServiceOrder management API")
public interface ServiceOrderOpenApiSpec {

    @Operation(summary = "Criar uma Ordem de Serviço", description = "Cria uma Ordem de Serviço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordem de Serviço criada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOrder.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<ServiceOrder> create(
        @Parameter(name = "CreateServiceOrder", description = "Dados da Ordem de Serviço", schema = @Schema(implementation = ServiceOrderRequestDTO.class))
        @Valid @RequestBody ServiceOrderRequestDTO serviceOrderRequestDTO);
}
