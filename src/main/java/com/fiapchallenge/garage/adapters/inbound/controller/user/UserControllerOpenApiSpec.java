package com.fiapchallenge.garage.adapters.inbound.controller.user;

import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "User", description = "User management API")
public interface UserControllerOpenApiSpec {

    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping(consumes = "application/json")
    ResponseEntity<User> create(
        @Parameter(name = "CreateUser", description = "Dados do usuário", schema = @Schema(implementation = CreateUserRequestDTO.class))
        @Valid @RequestBody CreateUserRequestDTO createUserRequestDTO);

}
