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
        return new Stock(
                UUID.randomUUID(),
                "Óleo Motor 5W30",
                "Óleo sintético para motor",
                50,
                new BigDecimal("45.90"),
                "Lubrificantes",
                LocalDateTime.now(),
                LocalDateTime.now(),
                10
        );
    }

    public static Stock createLowStock() {
        return new Stock(
                UUID.randomUUID(),
                "Filtro de Ar",
                "Filtro de ar para veículos",
                5,
                new BigDecimal("28.50"),
                "Filtros",
                LocalDateTime.now(),
                LocalDateTime.now(),
                10
        );
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