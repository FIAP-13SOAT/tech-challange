package com.fiapchallenge.garage.adapters.inbound.controller.vehicle;

import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRequestDTO;
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

@Tag(name = "Vehicle", description = "Vehicle management API")
public interface VehicleControllerOpenApiSpec {

    @Operation(summary = "Cadastrar um veículo", description = "Cadastra um novo veículo com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo cadastrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehicle.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<Vehicle> create(
        @Parameter(name = "CreateVehicle", description = "Dados do veículo", schema = @Schema(implementation = VehicleRequestDTO.class))
        @Valid @RequestBody VehicleRequestDTO customerRequestDTO);
}
