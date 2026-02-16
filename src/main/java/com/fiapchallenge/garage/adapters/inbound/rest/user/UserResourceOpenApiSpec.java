package com.fiapchallenge.garage.adapters.inbound.rest.user;

import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.LoginUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.LoginUserResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.UserDTO;
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
public interface UserResourceOpenApiSpec {

    @Operation(summary = "Criar um novo usuario", description = "Cria um novo usuario com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content)
    })
    @PostMapping(consumes = "application/json")
    ResponseEntity<UserDTO> create(
            @Parameter(name = "CreateUser", description = "Dados do usuario", schema = @Schema(implementation = CreateUserRequestDTO.class))
            @Valid @RequestBody CreateUserRequestDTO createUserRequestDTO);

    @Operation(summary = "Autenticar usuario", description = "Autentica usuario com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginUserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content)
    })
    @PostMapping(value = "/login", consumes = "application/json")
    ResponseEntity<LoginUserResponseDTO> login(
            @Parameter(name = "LoginUser", description = "Dados da autenticacao", schema = @Schema(implementation = LoginUserRequestDTO.class))
            @Valid @RequestBody LoginUserRequestDTO loginUserRequestDTO);
}
