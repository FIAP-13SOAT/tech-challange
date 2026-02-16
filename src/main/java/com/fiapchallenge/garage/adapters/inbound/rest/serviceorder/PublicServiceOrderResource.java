package com.fiapchallenge.garage.adapters.inbound.rest.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderTrackingDTO;
import com.fiapchallenge.garage.application.serviceorder.track.TrackServiceOrderUseCase;
import com.fiapchallenge.garage.controllers.serviceorder.PublicServiceOrderController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/public/service-orders")
@Tag(name = "Acompanhar ordem de servico", description = "API publica para acompanhamento de Ordem de Servico")
public class PublicServiceOrderResource {

    private final PublicServiceOrderController publicServiceOrderController;

    public PublicServiceOrderResource(TrackServiceOrderUseCase trackServiceOrderUseCase) {
        this.publicServiceOrderController = new PublicServiceOrderController(trackServiceOrderUseCase);
    }

    @Operation(summary = "Acompanhar ordem de servico", description = "Permite ao cliente acompanhar o status da sua ordem de servico")
    @GetMapping("/{serviceOrderId}/track")
    public ResponseEntity<ServiceOrderTrackingDTO> trackServiceOrder(
            @PathVariable UUID serviceOrderId,
            @RequestParam String cpfCnpj
    ) {
        return ResponseEntity.ok(publicServiceOrderController.trackServiceOrder(serviceOrderId, cpfCnpj));
    }
}
