package com.fiapchallenge.garage.adapters.inbound.rest.notification;

import com.fiapchallenge.garage.adapters.inbound.controller.notification.dto.NotificationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Notifications", description = "Operacoes relacionadas as notificacoes administrativas")
public interface NotificationResourceOpenApiSpec {

    @Operation(summary = "Listar todas as notificacoes", description = "Lista todas as notificacoes com paginacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<NotificationResponseDTO>> list(
            @Parameter(description = "Numero da pagina") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da pagina") @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Listar notificacoes nao lidas", description = "Lista apenas as notificacoes nao lidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<NotificationResponseDTO>> listUnread(
            @Parameter(description = "Numero da pagina") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da pagina") @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Marcar notificacao como lida", description = "Marca uma notificacao especifica como lida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificacao marcada como lida"),
            @ApiResponse(responseCode = "404", description = "Notificacao nao encontrada")
    })
    ResponseEntity<Void> markAsRead(@Parameter(description = "ID da notificacao") @PathVariable UUID id);
}
