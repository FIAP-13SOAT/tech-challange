package com.fiapchallenge.garage.unit.notification;

import com.fiapchallenge.garage.application.notification.create.CreateNotificationService;
import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationRepository;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.unit.notification.factory.NotificationTestFactory;
import com.fiapchallenge.garage.unit.stock.factory.StockTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateNotificationUnitTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private CreateNotificationService createNotificationService;

    private Stock lowStock;
    private Notification notification;

    @BeforeEach
    void setUp() {
        lowStock = StockTestFactory.createLowStock();
        notification = NotificationTestFactory.createLowStockNotification();
    }

    @Test
    void shouldCreateLowStockNotificationSuccessfully() {
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
            Notification savedNotification = invocation.getArgument(0);
            return savedNotification.setId(notification.getId());
        });

        Notification result = createNotificationService.createLowStockNotification(lowStock);

        assertNotNull(result);
        assertEquals("LOW_STOCK", result.getType());
        assertTrue(result.getMessage().contains(lowStock.getProductName()));
        assertFalse(result.isRead());
        assertEquals(lowStock.getId(), result.getStockId());
        
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void shouldCreateCorrectMessageFormat() {
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        createNotificationService.createLowStockNotification(lowStock);

        verify(notificationRepository).save(argThat(n -> 
            n.getMessage().contains("Estoque baixo para") &&
            n.getMessage().contains(lowStock.getProductName()) &&
            n.getMessage().contains("Quantidade atual: " + lowStock.getQuantity()) &&
            n.getMessage().contains("Mínimo: " + lowStock.getMinThreshold())
        ));
    }

    @Test
    void shouldSetNotificationAsUnread() {
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        createNotificationService.createLowStockNotification(lowStock);

        verify(notificationRepository).save(argThat(n -> !n.isRead()));
    }

    @Test
    void shouldSetCorrectStockId() {
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        createNotificationService.createLowStockNotification(lowStock);

        verify(notificationRepository).save(argThat(n -> 
            n.getStockId().equals(lowStock.getId())
        ));
    }
}