package com.fiapchallenge.garage.application.notification.create;

import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationGateway;
import com.fiapchallenge.garage.domain.stock.Stock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateNotificationService implements CreateNotificationUseCase {

    private final NotificationGateway notificationGateway;

    public CreateNotificationService(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    @Override
    public Notification createLowStockNotification(Stock stock) {
        String message = String.format("Estoque baixo para %s. Quantidade atual: %d, Mínimo: %d", 
                stock.getProductName(), stock.getQuantity(), stock.getMinThreshold());
        
        Notification notification = new Notification(
                null,
                "LOW_STOCK",
                message,
                stock.getId(),
                false,
                LocalDateTime.now()
        );

        return notificationGateway.save(notification);
    }
}