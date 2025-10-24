package com.fiapchallenge.garage.integration.internalnotification;

import com.fiapchallenge.garage.adapters.outbound.repositories.internalnotification.JpaInternalNotificationRepository;
import com.fiapchallenge.garage.application.internalnotification.create.CreateInternalNotificationService;
import com.fiapchallenge.garage.application.internalnotification.create.CreateInternalNotificationUseCase;
import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.domain.internalnotification.InternalNotification;
import com.fiapchallenge.garage.domain.internalnotification.NotificationType;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AcknowledgeInternalNotificationIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaInternalNotificationRepository internalNotificationRepository;
    private final CreateInternalNotificationService createInternalNotificationService;
    @Autowired
    private CreateUserService createUserService;

    @Autowired
    public AcknowledgeInternalNotificationIntegrationTest(MockMvc mockMvc,
                                                         JpaInternalNotificationRepository internalNotificationRepository,
                                                         CreateInternalNotificationService createInternalNotificationService) {
        this.mockMvc = mockMvc;
        this.internalNotificationRepository = internalNotificationRepository;
        this.createInternalNotificationService = createInternalNotificationService;
    }

    @Test
    @DisplayName("Deve reconhecer notificação interna com sucesso")
    void shouldAcknowledgeInternalNotificationSuccessfully() throws Exception {
        InternalNotification notification = createNotification(NotificationType.LOW_STOCK, "Low stock alert");
        User user = UserFixture.createUser(createUserService);

        mockMvc.perform(patch("/internal-notifications/{id}/acknowledge", notification.getId())
                .header("Authorization", getAuthToken(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notification.getId().toString()))
                .andExpect(jsonPath("$.acknowledged").value(true))
                .andExpect(jsonPath("$.userId").value(user.getId().toString()))
                .andExpect(jsonPath("$.acknowledgedAt").exists());

        var savedNotification = internalNotificationRepository.findById(notification.getId()).orElseThrow();
        assertThat(savedNotification.isAcknowledged()).isTrue();
        assertThat(savedNotification.getUserId()).isEqualTo(user.getId());
        assertThat(savedNotification.getAcknowledgedAt()).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar erro 404 para notificação inexistente")
    void shouldReturn404ForNonExistentNotification() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        User user = UserFixture.createUser(createUserService);

        mockMvc.perform(patch("/internal-notifications/{id}/acknowledge", nonExistentId)
                .header("Authorization", getAuthToken(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar erro para header Authorization ausente")
    void shouldReturnErrorForMissingAuthorizationHeader() throws Exception {
        InternalNotification notification = createNotification(NotificationType.LOW_STOCK, "Low stock alert");

        mockMvc.perform(patch("/internal-notifications/{id}/acknowledge", notification.getId()))
                .andExpect(status().isForbidden());
    }

    private InternalNotification createNotification(NotificationType type, String message) {
        CreateInternalNotificationUseCase.CreateInternalNotificationCommand command =
                new CreateInternalNotificationUseCase.CreateInternalNotificationCommand(type, UUID.randomUUID(), message);
        return createInternalNotificationService.handle(command);
    }
}
