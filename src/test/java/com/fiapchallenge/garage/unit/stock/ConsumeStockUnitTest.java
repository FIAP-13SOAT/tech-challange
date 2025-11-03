package com.fiapchallenge.garage.unit.stock;

import com.fiapchallenge.garage.application.stock.StockLevelChecker;
import com.fiapchallenge.garage.application.stock.consume.ConsumeStockService;
import com.fiapchallenge.garage.application.stockmovement.create.CreateStockMovementUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stock.command.ConsumeStockCommand;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.shared.exception.InsufficientStockException;
import com.fiapchallenge.garage.shared.exception.ResourceNotFoundException;
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
class ConsumeStockUnitTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockLevelChecker stockLevelChecker;

    @Mock
    private CreateStockMovementUseCase createStockMovementUseCase;

    @InjectMocks
    private ConsumeStockService consumeStockService;

    private Stock stock;
    private ConsumeStockCommand command;

    @BeforeEach
    void setUp() {
        stock = StockTestFactory.createStock();
        command = StockTestFactory.consumeStockCommand(stock.getId(), 10);
    }

    @Test
    @DisplayName("Deve consumir estoque com sucesso")
    void shouldConsumeStockSuccessfully() {
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock result = consumeStockService.handle(command);

        assertEquals(40, result.getQuantity());
        verify(stockRepository).save(any(Stock.class));
        verify(createStockMovementUseCase).logMovement(
                stock.getId(),
                StockMovement.MovementType.OUT,
                10,
                50,
                40,
                "Saída de estoque"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque não for encontrado")
    void shouldThrowExceptionWhenStockNotFound() {
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> consumeStockService.handle(command));
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque for insuficiente")
    void shouldThrowExceptionWhenInsufficientStock() {
        ConsumeStockCommand largeCommand = StockTestFactory.consumeStockCommand(stock.getId(), 100);
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));

        InsufficientStockException exception = assertThrows(
                InsufficientStockException.class,
                () -> consumeStockService.handle(largeCommand)
        );

        assertTrue(exception.getMessage().contains("Estoque insuficiente"));
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("Deve criar notificação quando estoque ficar baixo")
    void shouldCreateNotificationWhenStockBecomesLow() {
        Stock lowStock = StockTestFactory.createLowStock();
        ConsumeStockCommand lowCommand = StockTestFactory.consumeStockCommand(lowStock.getId(), 1);
        
        when(stockRepository.findById(lowStock.getId())).thenReturn(Optional.of(lowStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(lowStock);

        consumeStockService.handle(lowCommand);

        verify(stockLevelChecker).checkStockLevelAsync(any(Stock.class));
    }
}