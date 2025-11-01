package com.fiapchallenge.garage.adapters.inbound.controller.notification;

import com.fiapchallenge.garage.domain.notification.Notification;
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

@Tag(name = "Notifications", description = "Operações relacionadas às notificações administrativas")
public interface NotificationControllerOpenApiSpec {

    @Operation(summary = "Listar todas as notificações", description = "Lista todas as notificações com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<Notification>> list(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Listar notificações não lidas", description = "Lista apenas as notificações não lidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    ResponseEntity<Page<Notification>> listUnread(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Marcar notificação como lida", description = "Marca uma notificação específica como lida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificação marcada como lida"),
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    })
    ResponseEntity<Void> markAsRead(@Parameter(description = "ID da notificação") @PathVariable UUID id);
}