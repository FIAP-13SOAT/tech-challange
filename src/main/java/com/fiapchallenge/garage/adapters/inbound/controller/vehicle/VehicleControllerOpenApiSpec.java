package com.fiapchallenge.garage.adapters.inbound.controller.vehicle;

import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.UpdateVehicleRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.VehicleDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.VehicleRequestDTO;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "Vehicle", description = "Vehicle management API")
public interface VehicleControllerOpenApiSpec {

    @Operation(summary = "Listar veículos por cliente", description = "Lista todos os veículos de um cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<List<VehicleDTO>> listByCustomer(
        @Parameter(name = "customerId", description = "ID do cliente")
        @RequestParam UUID customerId);

    @Operation(summary = "Buscar veículo por ID", description = "Busca um veículo específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Veículo não encontrado", content = @Content)
    })
    ResponseEntity<VehicleDTO> findById(
        @Parameter(name = "id", description = "ID do veículo")
        @PathVariable UUID id);

    @Operation(summary = "Cadastrar um veículo", description = "Cadastra um novo veículo com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo cadastrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<VehicleDTO> create(
        @Parameter(name = "CreateVehicle", description = "Dados do veículo", schema = @Schema(implementation = VehicleRequestDTO.class))
        @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO);

    @Operation(summary = "Atualizar um veículo", description = "Atualiza os dados de um veículo existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou veículo não encontrado", content = @Content)
    })
    ResponseEntity<VehicleDTO> update(
        @Parameter(name = "id", description = "ID do veículo")
        @PathVariable UUID id,
        @Parameter(name = "UpdateVehicle", description = "Dados para atualização do veículo", schema = @Schema(implementation = UpdateVehicleRequestDTO.class))
        @Valid @RequestBody UpdateVehicleRequestDTO dto);

    @Operation(summary = "Deletar um veículo", description = "Remove um veículo do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Veículo deletado com sucesso", content = @Content),
        @ApiResponse(responseCode = "400", description = "Veículo não encontrado", content = @Content)
    })
    ResponseEntity<Void> delete(
        @Parameter(name = "id", description = "ID do veículo")
        @PathVariable UUID id);
}