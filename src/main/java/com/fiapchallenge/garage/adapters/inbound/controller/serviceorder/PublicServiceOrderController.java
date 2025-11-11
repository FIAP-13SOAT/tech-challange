package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderTrackingDTO;
import com.fiapchallenge.garage.application.serviceorder.TrackServiceOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/public/service-orders")
@Tag(name = "Acompanhar ordem de serviço", description = "API pública para acompanhamento de Ordem de Serviço")
public class PublicServiceOrderController {

    private final TrackServiceOrderUseCase trackServiceOrderUseCase;

    public PublicServiceOrderController(TrackServiceOrderUseCase trackServiceOrderUseCase) {
        this.trackServiceOrderUseCase = trackServiceOrderUseCase;
    }

    @Operation(summary = "Acompanhar ordem de serviço", description = "Permite ao cliente acompanhar o status da sua ordem de serviço")
    @GetMapping("/{serviceOrderId}/track")
    public ResponseEntity<ServiceOrderTrackingDTO> trackServiceOrder(
            @PathVariable UUID serviceOrderId,
            @RequestParam String cpfCnpj) {
        ServiceOrderTrackingDTO tracking = trackServiceOrderUseCase.handle(serviceOrderId, cpfCnpj);
        return ResponseEntity.ok(tracking);
    }
}
