package com.fiapchallenge.garage.adapters.inbound.rest.servicetype;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "ServiceType", description = "ServiceType management API")
public interface ServiceTypeResourceOpenApiSpec {

    @Operation(summary = "Criar uma Tipo de Servico", description = "Cria uma Tipo de Servico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de Servico criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content)
    })
    ResponseEntity<ServiceTypeDTO> create(
            @Parameter(name = "CreateServiceType", description = "Dados do Tipo de Servico", schema = @Schema(implementation = ServiceTypeRequestDTO.class))
            @Valid @RequestBody ServiceTypeRequestDTO serviceTypeRequestDTO);

    @Operation(summary = "Listar tipos de servico", description = "Lista todos os tipos de servico disponiveis")
    ResponseEntity<List<ServiceTypeDTO>> getAll();

    @Operation(summary = "Atualizar tipo de servico", description = "Atualiza um tipo de servico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de servico atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tipo de servico nao encontrado", content = @Content)
    })
    ResponseEntity<ServiceTypeDTO> update(
            @Parameter(name = "id", description = "ID do tipo de servico") @PathVariable UUID id,
            @Parameter(name = "UpdateServiceType", description = "Dados para atualizacao", schema = @Schema(implementation = UpdateServiceTypeDTO.class))
            @Valid @RequestBody UpdateServiceTypeDTO updateServiceTypeDTO);

    @Operation(summary = "Remover tipo de servico", description = "Remove um tipo de servico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de servico removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Tipo de servico nao encontrado", content = @Content)
    })
    ResponseEntity<Void> delete(
            @Parameter(name = "id", description = "ID do tipo de servico") @PathVariable UUID id);
}
