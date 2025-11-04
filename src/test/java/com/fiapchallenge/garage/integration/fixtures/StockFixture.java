package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.CreateStockRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.stock.dto.UpdateStockRequestDTO;

import java.math.BigDecimal;

public class StockFixture {

    public static CreateStockRequestDTO createStockRequest() {
        return new CreateStockRequestDTO(
                "Óleo Motor 5W30",
                "Óleo sintético para motor",
                new BigDecimal("45.90"),
                "Lubrificantes",
                5
        );
    }

    public static UpdateStockRequestDTO updateStockRequest() {
        return new UpdateStockRequestDTO(
                "Óleo Motor 10W40 Atualizado",
                "Óleo semi-sintético atualizado",
                new BigDecimal("52.90"),
                "Lubrificantes Premium",
                10
        );
    }

    public static String createStockJson() {
        return """
            {
                "productName": "Óleo Motor 5W30",
                "description": "Óleo sintético para motor",
                "unitPrice": 45.90,
                "category": "Lubrificantes",
                "minThreshold": 5
            }
            """;
    }

    public static String updateStockJson() {
        return """
            {
                "productName": "Óleo Motor 10W40 Atualizado",
                "description": "Óleo semi-sintético atualizado",
                "unitPrice": 52.90,
                "category": "Lubrificantes Premium",
                "minThreshold": 10
            }
            """;
    }
}