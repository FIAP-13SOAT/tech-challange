package com.fiapchallenge.garage.adapters.inbound.controller.servicetype;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.UpdateServiceTypeDTO;
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

@Tag(name = "ServiceType", description = "ServiceType management API")
public interface ServiceTypeOpenApiSpec {

    @Operation(summary = "Criar uma Tipo de Serviço", description = "Cria uma Tipo de Serviço")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de Serviço criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceTypeDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<ServiceTypeDTO> create(
        @Parameter(name = "CreateServiceType", description = "Dados do Tipo de Serviço", schema = @Schema(implementation = ServiceTypeRequestDTO.class))
        @Valid @RequestBody ServiceTypeRequestDTO serviceTypeRequestDTO);

    @Operation(summary = "Listar tipos de serviço", description = "Lista todos os tipos de serviço disponíveis")
    @GetMapping
    ResponseEntity<List<ServiceTypeDTO>> getAll();

    @Operation(summary = "Atualizar tipo de serviço", description = "Atualiza um tipo de serviço existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de serviço atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceTypeDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Tipo de serviço não encontrado", content = @Content)
    })
    ResponseEntity<ServiceTypeDTO> update(
        @Parameter(name = "id", description = "ID do tipo de serviço") @PathVariable UUID id,
        @Parameter(name = "UpdateServiceType", description = "Dados para atualização", schema = @Schema(implementation = UpdateServiceTypeDTO.class))
        @Valid @RequestBody UpdateServiceTypeDTO updateServiceTypeDTO);

    @Operation(summary = "Remover tipo de serviço", description = "Remove um tipo de serviço existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tipo de serviço removido com sucesso"),
        @ApiResponse(responseCode = "400", description = "Tipo de serviço não encontrado", content = @Content)
    })
    ResponseEntity<Void> delete(
        @Parameter(name = "id", description = "ID do tipo de serviço") @PathVariable UUID id);
}