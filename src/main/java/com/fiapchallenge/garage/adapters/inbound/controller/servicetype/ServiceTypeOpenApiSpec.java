package com.fiapchallenge.garage.adapters.inbound.controller.servicetype;

import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeRequestDTO;
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

@Tag(name = "ServiceType", description = "ServiceType management API")
public interface ServiceTypeOpenApiSpec {

    @Operation(summary = "Criar uma Tipo de Serviço", description = "Cria uma Tipo de Serviço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de Serviço criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceType.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<ServiceType> create(
        @Parameter(name = "CreateServiceType", description = "Dados do Tipo de Serviço", schema = @Schema(implementation = ServiceTypeRequestDTO.class))
        @Valid @RequestBody ServiceTypeRequestDTO serviceTypeRequestDTO);
}
