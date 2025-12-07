package com.fiapchallenge.garage.integration.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class StockMovementIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldConsumeStockAndCreateMovement() throws Exception {
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
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createStockJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(post("/stock/{id}/add", stockId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .param("quantity", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(50));

        mockMvc.perform(post("/stock/{id}/consume", stockId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(40));

        mockMvc.perform(get("/stock-movements")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .param("stockId", stockId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void shouldFailToConsumeInsufficientStock() throws Exception {
        String createStockJson = """
            {
                "productName": "Filtro Test",
                "description": "Filtro para teste",
                "unitPrice": 25.00,
                "category": "Filtros",
                "minThreshold": 2
            }
            """;

        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createStockJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(post("/stock/{id}/consume", stockId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .param("quantity", "100"))
                .andExpect(status().isBadRequest());
    }
}