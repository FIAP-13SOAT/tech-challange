package com.fiapchallenge.garage.adapters.inbound.controller.customer;

import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.CustomerRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.UpdateCustomerDTO;
import com.fiapchallenge.garage.domain.customer.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Customer", description = "Customer management API")
public interface CustomerControllerOpenApiSpec {

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<Customer> create(
        @Parameter(name = "CreateCustomer", description = "Dados do cliente", schema = @Schema(implementation = CustomerRequestDTO.class))
        @Valid @RequestBody CustomerRequestDTO customerRequestDTO);

    @PutMapping(value = "/{id}", consumes = "application/json")
    @Operation(summary = "Atualizar um cliente", description = "Atualiza um cliente existente com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    ResponseEntity<Customer> update(
        @Parameter(name = "id", description = "ID do cliente") @PathVariable UUID id,
        @Parameter(name = "updateCustomer", description = "Dados para atualizar cliente", schema = @Schema(implementation = UpdateCustomerDTO.class))
        @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO);

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna uma lista paginada de clientes com filtros opcionais")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)))
    })
    ResponseEntity<Page<Customer>> list(
        @Parameter(name = "name", description = "Filtrar por nome do cliente") @RequestParam(required = false) String name,
        @Parameter(name = "email", description = "Filtrar por email do cliente") @RequestParam(required = false) String email,
        @Parameter(name = "cpfCnpj", description = "Filtrar por CPF/CNPJ do cliente") @RequestParam(required = false) String cpfCnpj,
        @Parameter(name = "page", description = "Número da página (inicia em 0)") @RequestParam(required = false) Integer page,
        @Parameter(name = "size", description = "Tamanho da página") @RequestParam(required = false) Integer size);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um cliente", description = "Remove um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    ResponseEntity<Void> delete(
        @Parameter(name = "id", description = "ID do cliente") @PathVariable UUID id);
}
