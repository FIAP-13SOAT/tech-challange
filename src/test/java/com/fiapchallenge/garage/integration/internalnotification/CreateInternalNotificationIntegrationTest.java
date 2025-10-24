package com.fiapchallenge.garage.integration.internalnotification;

import com.fiapchallenge.garage.adapters.outbound.entities.InternalNotificationEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.internalnotification.JpaInternalNotificationRepository;
import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CreateInternalNotificationIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaInternalNotificationRepository internalNotificationRepository;
    @Autowired
    private CreateUserService createUserService;

    @Autowired
    public CreateInternalNotificationIntegrationTest(MockMvc mockMvc, JpaInternalNotificationRepository internalNotificationRepository) {
        this.mockMvc = mockMvc;
        this.internalNotificationRepository = internalNotificationRepository;
    }

    @Test
    @DisplayName("Deve criar notificação interna e persistir")
    void shouldCreateInternalNotificationAndPersistToDatabase() throws Exception {
        User user = UserFixture.createUser(createUserService);
        UUID resourceId = UUID.randomUUID();
        String notificationJson = String.format("""
                {
                  "type": "LOW_STOCK",
                  "resourceId": "%s",
                  "message": "Low stock alert"
                }
                """, resourceId);

        mockMvc.perform(post("/internal-notifications")
                .header("Authorization", getAuthToken(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(notificationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("LOW_STOCK"))
                .andExpect(jsonPath("$.resourceId").value(resourceId.toString()))
                .andExpect(jsonPath("$.message").value("Low stock alert"))
                .andExpect(jsonPath("$.acknowledged").value(false))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.acknowledgedAt").doesNotExist());

        InternalNotificationEntity savedNotification = internalNotificationRepository.findAll().get(0);
        assertThat(savedNotification.getType().name()).isEqualTo("LOW_STOCK");
        assertThat(savedNotification.getResourceId().toString()).isEqualTo(resourceId.toString());
        assertThat(savedNotification.getMessage()).isEqualTo("Low stock alert");
        assertThat(savedNotification.isAcknowledged()).isFalse();
        assertThat(savedNotification.getCreatedAt()).isNotNull();
        assertThat(savedNotification.getAcknowledgedAt()).isNull();
        assertThat(savedNotification.getId()).isNotNull();
    }
}
