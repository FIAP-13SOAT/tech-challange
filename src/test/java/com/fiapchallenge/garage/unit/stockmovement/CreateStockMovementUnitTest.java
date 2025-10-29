package com.fiapchallenge.garage.unit.stockmovement;

import com.fiapchallenge.garage.application.stockmovement.create.CreateStockMovementService;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.domain.stockmovement.StockMovementRepository;
import com.fiapchallenge.garage.unit.stockmovement.factory.StockMovementTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateStockMovementUnitTest {

    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private CreateStockMovementService createStockMovementService;

    @Test
    void shouldLogInMovementSuccessfully() {
        StockMovement movement = StockMovementTestFactory.createInMovement();
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(movement);

        createStockMovementService.logMovement(
                UUID.randomUUID(),
                StockMovement.MovementType.IN,
                20,
                50,
                70,
                "Entrada de estoque"
        );

        verify(stockMovementRepository).save(any(StockMovement.class));
    }

    @Test
    void shouldLogOutMovementSuccessfully() {
        StockMovement movement = StockMovementTestFactory.createOutMovement();
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(movement);

        createStockMovementService.logMovement(
                UUID.randomUUID(),
                StockMovement.MovementType.OUT,
                10,
                50,
                40,
                "Saída de estoque"
        );

        verify(stockMovementRepository).save(any(StockMovement.class));
    }
}