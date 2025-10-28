package com.fiapchallenge.garage.application.notification.create;

import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.stock.Stock;

public interface CreateNotificationUseCase {
    Notification createLowStockNotification(Stock stock);
}