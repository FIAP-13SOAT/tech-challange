package com.fiapchallenge.garage.integration.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class StockNotificationIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateStockAddQuantityConsumeAndGenerateNotification() throws Exception {
        String createStockJson = """
            {
                "productName": "Filtro de Ar Test",
                "description": "Filtro para teste de integração",
                "unitPrice": 25.00,
                "category": "Filtros",
                "minThreshold": 10
            }
            """;

        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createStockJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Filtro de Ar Test"))
                .andExpect(jsonPath("$.quantity").value(0))
                .andExpect(jsonPath("$.minThreshold").value(10))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(post("/stock/{id}/add", stockId)
                        .header("Authorization", getAuthToken())
                        .param("quantity", "15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(15));
        
        mockMvc.perform(post("/stock/{id}/consume", stockId)
                        .header("Authorization", getAuthToken())
                        .param("quantity", "8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(7));

        mockMvc.perform(get("/notifications/unread")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[?(@.type == 'LOW_STOCK' && @.stockId == '" + stockId + "')]").exists())
                .andExpect(jsonPath("$.content[?(@.message =~ /.*Estoque baixo.*Filtro de Ar Test.*/)]").exists());

        String unreadResponse = mockMvc.perform(get("/notifications/unread")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String notificationId = objectMapper.readTree(unreadResponse)
                .get("content")
                .get(0)
                .get("id")
                .asText();

        mockMvc.perform(put("/notifications/{id}/read", notificationId)
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/notifications/unread")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.id == '" + notificationId + "')]").doesNotExist());

        mockMvc.perform(get("/notifications")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.id == '" + notificationId + "' && @.read == true)]").exists());
    }

    @Test
    void shouldNotGenerateNotificationWhenStockIsNotLow() throws Exception {
        String createStockJson = """
            {
                "productName": "Óleo Motor Test",
                "description": "Óleo para teste",
                "unitPrice": 45.90,
                "category": "Lubrificantes",
                "minThreshold": 5
            }
            """;

        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createStockJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(post("/stock/{id}/add", stockId)
                        .header("Authorization", getAuthToken())
                        .param("quantity", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(50));

        mockMvc.perform(post("/stock/{id}/consume", stockId)
                        .header("Authorization", getAuthToken())
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(40)); // 40 > 5 (threshold)

        mockMvc.perform(get("/notifications/unread")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.stockId == '" + stockId + "')]").doesNotExist());
    }
}