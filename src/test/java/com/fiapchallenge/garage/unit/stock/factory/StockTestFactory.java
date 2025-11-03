package com.fiapchallenge.garage.unit.stock.factory;

import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.command.AddStockCommand;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.domain.stock.command.CreateStockCommand;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class StockTestFactory {

    public static Stock createStock() {
        return Stock.builder()
                .id(UUID.randomUUID())
                .productName("Óleo Motor 5W30")
                .description("Óleo sintético para motor")
                .quantity(50)
                .unitPrice(new BigDecimal("45.90"))
                .category("Lubrificantes")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .minThreshold(10)
                .build();
    }

    public static Stock createLowStock() {
        return Stock.builder()
                .id(UUID.randomUUID())
                .productName("Filtro de Ar")
                .description("Filtro de ar para veículos")
                .quantity(5)
                .unitPrice(new BigDecimal("28.50"))
                .category("Filtros")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .minThreshold(10)
                .build();
    }

    public static CreateStockCommand createStockCommand() {
        return new CreateStockCommand(
                "Pastilha de Freio",
                "Pastilha de freio dianteira",
                new BigDecimal("89.90"),
                "Freios",
                3
        );
    }

    public static AddStockCommand addStockCommand(UUID stockId, Integer quantity) {
        return new AddStockCommand(stockId, quantity);
    }

    public static ConsumeStockCommand consumeStockCommand(UUID stockId, Integer quantity) {
        return new ConsumeStockCommand(stockId, quantity);
    }
}