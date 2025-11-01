package com.fiapchallenge.garage.unit.notification;

import com.fiapchallenge.garage.application.notification.list.ListNotificationService;
import com.fiapchallenge.garage.domain.notification.Notification;
import com.fiapchallenge.garage.domain.notification.NotificationRepository;
import com.fiapchallenge.garage.unit.notification.factory.NotificationTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListNotificationUnitTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private ListNotificationService listNotificationService;

    @Test
    @DisplayName("Deve listar todas as notificações")
    void shouldListAllNotifications() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Notification> notifications = List.of(
                NotificationTestFactory.createLowStockNotification(),
                NotificationTestFactory.createReadNotification()
        );
        Page<Notification> page = new PageImpl<>(notifications, pageable, notifications.size());
        
        when(notificationRepository.findAll(pageable)).thenReturn(page);

        Page<Notification> result = listNotificationService.handle(pageable);

        assertEquals(2, result.getContent().size());
        verify(notificationRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve listar apenas notificações não lidas")
    void shouldListOnlyUnreadNotifications() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Notification> unreadNotifications = List.of(
                NotificationTestFactory.createLowStockNotification()
        );
        Page<Notification> page = new PageImpl<>(unreadNotifications, pageable, unreadNotifications.size());
        
        when(notificationRepository.findByReadFalse(pageable)).thenReturn(page);

        Page<Notification> result = listNotificationService.handleUnread(pageable);

        assertEquals(1, result.getContent().size());
        assertFalse(result.getContent().get(0).isRead());
        verify(notificationRepository).findByReadFalse(pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não houver notificações")
    void shouldReturnEmptyPageWhenNoNotifications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Notification> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        
        when(notificationRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<Notification> result = listNotificationService.handle(pageable);

        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }
}