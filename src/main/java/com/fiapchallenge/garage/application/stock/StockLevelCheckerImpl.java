package com.fiapchallenge.garage.application.stock;

import com.fiapchallenge.garage.application.notification.create.CreateNotificationUseCase;
import com.fiapchallenge.garage.domain.stock.Stock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StockLevelCheckerImpl implements StockLevelChecker {
    private final CreateNotificationUseCase createNotificationUseCase;

    public StockLevelCheckerImpl(CreateNotificationUseCase createNotificationUseCase) {
        this.createNotificationUseCase = createNotificationUseCase;
    }

    @Override
    @Async
    public void checkStockLevelAsync(Stock stock) {
        if (stock.isLowStock()) {
            createNotificationUseCase.createLowStockNotification(stock);
        }
    }
}