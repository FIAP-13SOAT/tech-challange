package com.fiapchallenge.garage.unit.stock;

import com.fiapchallenge.garage.application.stock.add.AddStockService;
import com.fiapchallenge.garage.application.stockmovement.create.CreateStockMovementUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stock.command.AddStockCommand;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.unit.stock.factory.StockTestFactory;
import org.junit.jupiter.api.BeforeEach;
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
    private StockRepository stockRepository;

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
    void shouldAddStockSuccessfully() {
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock result = addStockService.handle(command);

        assertEquals(70, result.getQuantity());
        verify(stockRepository).save(any(Stock.class));
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
    void shouldThrowExceptionWhenStockNotFound() {
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> addStockService.handle(command));
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    void shouldUpdateTimestamp() {
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        addStockService.handle(command);

        verify(stockRepository).save(argThat(s -> s.getUpdatedAt() != null));
    }
}