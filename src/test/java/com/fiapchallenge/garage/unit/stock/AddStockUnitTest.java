package com.fiapchallenge.garage.unit.stock;

import com.fiapchallenge.garage.application.stock.add.AddStockService;
import com.fiapchallenge.garage.application.stock.exceptions.StockNotFoundException;
import com.fiapchallenge.garage.application.stockmovement.create.CreateStockMovementUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import com.fiapchallenge.garage.application.stock.command.AddStockCommand;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.unit.stock.factory.StockTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddStockUnitTest {

    @Mock
    private StockGateway stockGateway;

    @Mock
    private CreateStockMovementUseCase createStockMovementUseCase;

    @InjectMocks
    private AddStockService addStockService;

    private Stock stock;
    private AddStockCommand command;

    @BeforeEach
    void setUp() {
        stock = StockTestFactory.createStock();
        command = StockTestFactory.addStockCommand(stock.getId(), 20);
    }

    @Test
    @DisplayName("Deve adicionar estoque com sucesso")
    void shouldAddStockSuccessfully() {
        when(stockGateway.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockGateway.save(any(Stock.class))).thenReturn(stock);

        Stock result = addStockService.handle(command);

        assertEquals(70, result.getQuantity());
        verify(stockGateway).save(any(Stock.class));
        verify(createStockMovementUseCase).logMovement(
                stock.getId(),
                StockMovement.MovementType.IN,
                20,
                50,
                70,
                "Entrada de estoque"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque não for encontrado")
    void shouldThrowExceptionWhenStockNotFound() {
        when(stockGateway.findById(stock.getId())).thenReturn(Optional.empty());

        assertThrows(StockNotFoundException.class, () -> addStockService.handle(command));
        verify(stockGateway, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("Deve atualizar timestamp ao adicionar estoque")
    void shouldUpdateTimestamp() {
        when(stockGateway.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockGateway.save(any(Stock.class))).thenReturn(stock);

        addStockService.handle(command);

        verify(stockGateway).save(argThat(s -> s.getUpdatedAt() != null));
    }
}
