package com.fiapchallenge.garage.adapters.inbound.controller.customer;

import com.fiapchallenge.garage.application.commands.customer.UpdateCustomerCmd;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.CustomerRequestDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Customer", description = "Customer management API")
public interface CustomerControllerOpenApiSpec {

    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping(consumes = "application/json")
    ResponseEntity<Customer> create(
        @Parameter(name = "CreateCustomer", description = "Dados do cliente", schema = @Schema(implementation = CustomerRequestDTO.class))
        @Valid @RequestBody CustomerRequestDTO customerRequestDTO);

    @Operation(summary = "Atualizar um cliente", description = "Atualiza um cliente existente com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PutMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Customer> update(
        @Parameter(name = "id", description = "ID do cliente") @PathVariable UUID id,
        @Parameter(name = "updateCustomer", description = "Dados para atualizar cliente", schema = @Schema(implementation = UpdateCustomerCmd.class))
        @Valid @RequestBody UpdateCustomerCmd updateCustomerCmd);
}
