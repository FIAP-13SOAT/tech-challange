package com.fiapchallenge.garage.integration.internalnotification;

import com.fiapchallenge.garage.adapters.outbound.repositories.internalnotification.JpaInternalNotificationRepository;
import com.fiapchallenge.garage.application.internalnotification.create.CreateInternalNotificationService;
import com.fiapchallenge.garage.domain.internalnotification.NotificationType;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.InternalNotificationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ListInternalNotificationIntegrationtest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaInternalNotificationRepository internalNotificationRepository;
    private final CreateInternalNotificationService createInternalNotificationService;

    @Autowired
    public ListInternalNotificationIntegrationtest(MockMvc mockMvc, JpaInternalNotificationRepository internalNotificationRepository, CreateInternalNotificationService createInternalNotificationService) {
        this.mockMvc = mockMvc;
        this.internalNotificationRepository = internalNotificationRepository;
        this.createInternalNotificationService = createInternalNotificationService;
    }

    @Test
    @DisplayName("Deve listar todas as notificações internas")
    void shouldListAllInternalNotifications() throws Exception {
        InternalNotificationFixture.createNotification(createInternalNotificationService, NotificationType.LOW_STOCK, "Low stock alert");
        InternalNotificationFixture.createNotification(createInternalNotificationService, NotificationType.OUT_OF_STOCK, "Out of stock alert");

        mockMvc.perform(get("/internal-notifications").header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].type").value("LOW_STOCK"))
                .andExpect(jsonPath("$.content[1].type").value("OUT_OF_STOCK"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));

        assertThat(internalNotificationRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("Deve paginar corretamente as notificações")
    void shouldPaginateNotificationsCorrectly() throws Exception {
        for (int i = 1; i <= 5; i++) {
            InternalNotificationFixture.createNotification(createInternalNotificationService, NotificationType.LOW_STOCK, "Message " + i);
        }

        mockMvc.perform(get("/internal-notifications?page=0&size=2").header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(false));

        mockMvc.perform(get("/internal-notifications?page=2&size=2").header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.number").value(2))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(true));
    }


}
