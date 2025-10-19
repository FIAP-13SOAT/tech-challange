package com.fiapchallenge.garage.unit.internalnotification;

import com.fiapchallenge.garage.application.internalnotification.CreateInternalNotificationService;
import com.fiapchallenge.garage.application.internalnotification.command.CreateInternalNotificationCommand;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotificationRepository;
import com.fiapchallenge.garage.unit.internalnotification.utils.factory.InternalNotificationTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateInternalNotificationUnitTest {

    @Mock
    private InternalNotificationRepository internalNotificationRepository;

    @InjectMocks
    private CreateInternalNotificationService createInternalNotificationService;

    @Test
    @DisplayName("Deve criar notificação interna")
    void shouldCreateInternalNotification() {
        when(internalNotificationRepository.save(any(InternalNotification.class)))
                .thenReturn(InternalNotificationTestFactory.createInternalNotification());

        CreateInternalNotificationCommand command = InternalNotificationTestFactory.createCommand();
        InternalNotification result = createInternalNotificationService.handle(command);

        assertEquals(InternalNotificationTestFactory.ID, result.getId());
        assertEquals(InternalNotificationTestFactory.TYPE, result.getType());
        assertEquals(InternalNotificationTestFactory.USER_ID, result.getUserId());
        assertEquals(InternalNotificationTestFactory.RESOURCE_ID, result.getResourceId());
        assertEquals(InternalNotificationTestFactory.MESSAGE, result.getMessage());
        assertFalse(result.isAcknowledged());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getAcknowledgedAt());

        verify(internalNotificationRepository).save(any(InternalNotification.class));
    }
}
